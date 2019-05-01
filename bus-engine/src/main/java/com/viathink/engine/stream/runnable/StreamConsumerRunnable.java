package com.viathink.engine.stream.runnable;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.viathink.core.batch.service.WatchdogService;
import com.viathink.core.business.gene.bean.ErrorLogEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.mapper.ErrorLogMapper;
import com.viathink.core.business.service.CustomerEventService;
import com.viathink.core.log.bean.GeneLogEntity;
import com.viathink.core.log.service.GeneLogService;
import com.viathink.core.monitor.dto.ErrorDetailFieldCheckDto;
import com.viathink.core.monitor.service.MonitorService;
import com.viathink.core.monitor.util.MonitorUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.engine.collection.runnable.ProducerRunnable;
import com.viathink.engine.common.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class StreamConsumerRunnable implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ProducerRunnable.class);
    private String topic;
    private String tag;
    private OrderConsumer orderConsumer;
    private WatchdogService watchdogService;
    private GeneLogService geneLogService;
    private MonitorService monitorService;
    private ErrorLogMapper errorLogMapper;
    private CustomerEventService customerEventServiceImpl;

    public StreamConsumerRunnable(String topic,
                                  String tag,
                                  String consumerId,
                                  MqConfig mqConfig,
                                  WatchdogService watchdogService,
                                  GeneLogService geneLogService,
                                  MonitorService monitorService,
                                  ErrorLogMapper errorLogMapper,
                                  CustomerEventService customerEventServiceImpl
    ) {

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumerId);
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, mqConfig.getOnsAddr());
        // 顺序消息消费失败进行重试前的等待时间 单位(毫秒)
        consumerProperties.put(PropertyKeyConst.SuspendTimeMillis, mqConfig.getSuspendTimeMillis());
        // 消息消费失败时的最大重试次数
        consumerProperties.put(PropertyKeyConst.MaxReconsumeTimes, mqConfig.getMaxReconsumeTimes());
        this.orderConsumer = ONSFactory.createOrderedConsumer(consumerProperties);
        this.topic = topic;
        this.tag = tag;
        this.watchdogService = watchdogService;
        this.geneLogService = geneLogService;
        this.monitorService = monitorService;
        this.errorLogMapper = errorLogMapper;
        this.customerEventServiceImpl = customerEventServiceImpl;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getId() + "日志线程正在运行...");
        orderConsumer.subscribe(
                // Message 所属的 Topic
                this.topic,
                // 订阅指定 Topic 下的 Tags：
                // 1. * 表示订阅所有消息
                // 2. TagA || TagB || TagC 表示订阅 TagA 或 TagC 或 TagD 的消息
                this.tag,
                new MessageOrderListener() {
                    /**
                     * 1. 消息消费处理失败或者处理出现异常，返回 OrderAction.Suspend<br>
                     * 2. 消息处理成功，返回与返回 OrderAction.Success
                     */
                    @Override
                    public OrderAction consume(Message message, ConsumeOrderContext context) {
                        JSONObject jsonObject = null;
                        logger.info("开始消费 : " + new Date());
                        try {
                            jsonObject = JSONObject.parseObject(new String(message.getBody()));

                            GeneLogEntity geneLogEntity = new GeneLogEntity();

                            if(jsonObject.getJSONObject("data") != null && jsonObject.getJSONObject("data").getString("orderId")!= null)
                                geneLogEntity.setOrderId(jsonObject.getJSONObject("data").getString("orderId"));
                            geneLogEntity.setTag(jsonObject.getString("tag"));
                            geneLogEntity.setEvent(jsonObject.getString("event"));
                            geneLogEntity.setEventTime(Long.valueOf(jsonObject.getString("eventTime")));
                            geneLogEntity.setMessageId(message.getMsgID());
                            geneLogEntity.setRecordId(Long.valueOf(jsonObject.getString("id")));
                            BusinessDate businessDate = BusinessDateUtil.getBusinessDate(Long.valueOf(jsonObject.getString("eventTime")));
                            geneLogEntity.setDayStr(businessDate.getDayStr());
                            geneLogEntity.setMonthStr(businessDate.getMonthStr());
                            geneLogEntity.setYearStr(businessDate.getYearStr());
                            geneLogEntity.setQuarterStr(businessDate.getQuarterStr());
                            geneLogEntity.setHalfYearStr(businessDate.getHalfYearStr());
                            geneLogEntity.setRecord(new String(message.getBody()));

                            String eventStr = jsonObject.getString("event");
                            EventBaseEntity event = new EventBaseEntity();
                            event.setTag(jsonObject.getString("tag"));
                            event.setEvent(eventStr);
                            event.setEventTime(Long.valueOf(jsonObject.getString("eventTime")));
                            event.setMessageId(message.getMsgID());
                            event.setRecordId(Long.valueOf(jsonObject.getString("id")));
                            event.setDayStr(businessDate.getDayStr());
                            event.setMonthStr(businessDate.getMonthStr());
                            event.setYearStr(businessDate.getYearStr());
                            event.setQuarterStr(businessDate.getQuarterStr());
                            event.setHalfYearStr(businessDate.getHalfYearStr());
                            // recordId监控
                            boolean recordIdExists = watchdogService.recordIdCheck(event);
                            // 记录消息
                            geneLogService.addLog(geneLogEntity);
                            if (recordIdExists) {
                                return OrderAction.Success;
                            }
                            // 字段检查
                            if(!monitorService.messageCheck(jsonObject,message.getMsgID()))
                                return OrderAction.Success;
                            // 批任务监听
                            watchdogService.watch(event, businessDate);
                            // 执行流式计算
                            customerEventServiceImpl.eventHandler(event,jsonObject);
                            logger.info("消费成功 : " + new Date()+"-----"+jsonObject.getString("id"));
                            return OrderAction.Success;
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("consume error: " + e.getMessage());
                            logger.error("consume error message obj: " + message.toString());

                            if(jsonObject != null) {
                                ErrorLogEntity resultDto = new ErrorLogEntity();
                                ErrorDetailFieldCheckDto fieldCheckDto = new ErrorDetailFieldCheckDto();
                                List<String> list = new ArrayList<>();

                                fieldCheckDto.setMessageId(message.getMsgID());
                                fieldCheckDto.setEvent(jsonObject.getString("event"));
                                fieldCheckDto.setEventTime(jsonObject.getLong("eventTime"));
                                fieldCheckDto.setRecordId(jsonObject.getLong("id"));
                                fieldCheckDto.setTag(jsonObject.getString("tag"));
                                resultDto.setError("消费者解析数据时抛出异常");
                                resultDto.setType(MonitorUtil.ERROR_TYPE_EXCEPTION);
                                resultDto.setStatus(false);
                                list.add(e.getMessage());
                                fieldCheckDto.setErrorList(list);
                                resultDto.setDetail(JSONObject.toJSONString(fieldCheckDto));
                                errorLogMapper.addErrorLog(resultDto);
                            }
                            return OrderAction.Success;
                        }
                    }
                });
        orderConsumer.start();
    }
}
