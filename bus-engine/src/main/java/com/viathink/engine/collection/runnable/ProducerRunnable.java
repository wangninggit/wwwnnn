package com.viathink.engine.collection.runnable;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.viathink.core.collection.service.CollectionService;
import com.viathink.engine.common.config.MqConfig;
import com.viathink.engine.component.*;
import com.viathink.engine.mq.MqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ProducerRunnable implements Runnable{

    private Logger logger = LoggerFactory.getLogger(ProducerRunnable.class);
    private ReleaseManager ReleaseManager;
    private String topic;
    private MqConfig mqConfig;
    private MqProducer mqProducer;
    private CollectionService collectionService;

    public ProducerRunnable(ReleaseManager releaseManager, String topic, MqProducer mqProducer, MqConfig mqConfig, CollectionService collectionService) {
        ReleaseManager = releaseManager;
        this.topic = topic;
        this.mqConfig = mqConfig;
        this.mqProducer = mqProducer;
        this.collectionService = collectionService;
    }

    @Override
    public void run() {
        ReleaseManager.startCounterIncrement();
        while (ReleaseManager.getStopping()) {//不收集数据了
            logger.info("数据收集线程正在运行...");
            List<Map<String, Object>> collectionData = collectionService.getCollectionData();
            if(collectionData != null) {
                for (Map<String, Object> map : collectionData) {
                    String tag = (String) map.get("tag");
                    String jsonString = JSON.toJSONString(map);
                    Message message = new Message(
                            // Message 所属的 Topic
                            this.topic,
                            // Message Tag,
                            // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                            tag,
                            // Message Body
                            // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                            jsonString.getBytes());
                    String orderId = this.mqConfig.getProducerId() + Math.random();
                    message.setKey(orderId);
                    // 分区顺序消息中区分不同分区的关键字段。
                    // 全局顺序消息，该字段可以设置为任意非空字符串
                    String shardingKey = String.valueOf("simpleMQProducer-" + this.mqConfig.getProducerId());
                    if(mqProducer.sendMessage(message, shardingKey)){
                        logger.info("生产成功，id = " + map.get("id"));
                        collectionService.updateRecordId(Long.valueOf(map.get("id").toString()));
                    }else{
                        logger.warn("生产消息失败，终止本次生产");
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                logger.error("获取同步数据失败");
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("数据收集线程已经结束...");
        ReleaseManager.stopCounterIncrement();
    }
}


