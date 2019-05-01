package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.*;
import com.viathink.core.business.gene.dao.PaymentInfoDao;
import com.viathink.core.business.gene.mapper.OrderLogisticsMapper;
import com.viathink.core.business.gene.service.OrderEventService;
import com.viathink.core.user.bean.IntegralCoefficientEntity;
import com.viathink.core.user.mapper.IntegralCoefficientMapper;
import com.viathink.core.util.EventHandler;
import com.viathink.core.util.GeneDetailBaseEntityUtil;
import com.viathink.core.util.TestingItemDetailBaseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventServiceImpl implements OrderEventService {
    private final GeneDetailBaseEntityUtil geneDetailBaseEntityUtil;
    private final TestingItemDetailBaseEntityUtil testingItemDetailBaseEntityUtil;
    private final OrderLogisticsMapper orderLogisticsMapper;
    private final PaymentInfoDao paymentInfoDaoImpl;
    private final IntegralCoefficientMapper integralCoefficientMapper;

    @Autowired
    public OrderEventServiceImpl(TestingItemDetailBaseEntityUtil testingItemDetailBaseEntityUtil, GeneDetailBaseEntityUtil geneDetailBaseEntityUtil, OrderLogisticsMapper orderLogisticsMapper, PaymentInfoDao paymentInfoDaoImpl, IntegralCoefficientMapper integralCoefficientMapper) {
        this.testingItemDetailBaseEntityUtil = testingItemDetailBaseEntityUtil;
        this.geneDetailBaseEntityUtil = geneDetailBaseEntityUtil;
        this.orderLogisticsMapper = orderLogisticsMapper;
        this.paymentInfoDaoImpl = paymentInfoDaoImpl;
        this.integralCoefficientMapper = integralCoefficientMapper;
    }

    @Override
    public void orderCreated(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event,object,new EventHandler<GeneDetailBaseEntity>(){
            @Override
            public GeneDetailBaseEntity perfectDataWhenOldDetailIsNull(GeneDetailBaseEntity messageDetail) {
                messageDetail.setOrderCreateTime(messageDetail.getEventTime());
                messageDetail.setOrderIncome(messageDetail.getOrderPrice());
                messageDetail.setOrderPlaceCost(messageDetail.getOrderPrice());
                messageDetail.setOrderPlaceCount(1L);
                return messageDetail;
            }
        });

        // 关联套餐明细创建事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>() {
            @Override
            public TestingItemDetailBaseEntity perfectDataWhenOldDetailIsNull(TestingItemDetailBaseEntity messageDetail) {
                messageDetail.setTestingItemOrderIncome(messageDetail.getTestingItemPrice());
                messageDetail.setOrderCreateTime(messageDetail.getEventTime());
                return messageDetail;
            }
        });
    }

    @Override
    public void orderPay(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event, object, new EventHandler<GeneDetailBaseEntity>() {
            @Override
            public void perfectDataAtTheBeginning(GeneDetailBaseEntity messageDetail) {
                paymentInfoDaoImpl.addPayment(messageDetail,PaymentInfoDao.TYPE_PAY);
            }

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempGeneDetail, GeneDetailBaseEntity mergeGeneDetaill) {
                tempGeneDetail.setCashIncome(mergeGeneDetaill.getPayTotal() + tempGeneDetail.getCashIncome());
                tempGeneDetail.setOrderPayTime(mergeGeneDetaill.getEventTime());
                return tempGeneDetail;
            }
        });

        // 关联套餐明细支付事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>() {

            @Override
            public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetaill) {
                tempDetail.setTestingItemCashIncome(mergeDetaill.getTestingItemOrderIncome());
                tempDetail.setOrderPayTime(mergeDetaill.getEventTime());
                return tempDetail;
            }
        });
    }

    @Override
    public void sampleConfirmed(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event, object, new EventHandler<GeneDetailBaseEntity>() {
            private Long testingItemCost = 0L;

            @Override
            public void perfectDataAtTheBeginning(GeneDetailBaseEntity messageGeneDetail) {
                for (TestingItemEntity testingItemEntity : messageGeneDetail.getTestings()) {
                    if(messageGeneDetail.getTestingItemId().equals(testingItemEntity.getTestingItemId())){
                        testingItemCost = testingItemEntity.getTestingItemCost();
                    }
                }
            }

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempGeneDetail, GeneDetailBaseEntity mergeGeneDetail) {
                tempGeneDetail.setSampleConfirmTime(mergeGeneDetail.getEventTime());
                tempGeneDetail.setTestingItemCost(testingItemCost + tempGeneDetail.getTestingItemCost());
                return tempGeneDetail;
            }
        });
        // 关联套餐明细样本确认事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>() {
            GeneDetailBaseEntity geneDetailBaseEntity = object.toJavaObject(GeneDetailBaseEntity.class);

            @Override
            public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail) {
                if (geneDetailBaseEntity.getTestingItemId().equals(mergeDetail.getTestingItemId())) {
                    tempDetail.setTestingItemRecordCost(mergeDetail.getTestingItemCost() - mergeDetail.getTestingItemRecordCost() + tempDetail.getTestingItemRecordCost());
                    tempDetail.setSampleConfirmTime(mergeDetail.getEventTime());
                }
                return tempDetail;
            }
        });
    }

    @Override
    public void testingReportUploaded(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event,object,new EventHandler<GeneDetailBaseEntity>(){

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempDetail, GeneDetailBaseEntity mergeDetail) {
                tempDetail.setTestingReportUploadTime(mergeDetail.getEventTime());
                return tempDetail;
            }
        });

        // 关联套餐明细检测报告上传事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>(){
            GeneDetailBaseEntity geneDetailBaseEntity = object.toJavaObject(GeneDetailBaseEntity.class);

            @Override
            public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail) {
                if(geneDetailBaseEntity.getTestingItemId().equals(mergeDetail.getTestingItemId())) {
                    tempDetail.setTestingReportUploadTime(mergeDetail.getEventTime());
                }
                return tempDetail;
            }
        });
    }

    @Override
    public void orderInvoiced(EventBaseEntity event, JSONObject object) {
        // 基因表开票事件
        geneDetailBaseEntityUtil.orderInvoice(event,object);
        // 关联套餐明细订单开票事件
        testingItemDetailBaseEntityUtil.testingItemInvoiced(event, object);
    }

    @Override
    public void integralGranted(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event,object,new EventHandler<GeneDetailBaseEntity>(){

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempDetail, GeneDetailBaseEntity mergeDetail) {
                tempDetail.setIntegralGrantTime(mergeDetail.getEventTime());
                tempDetail.setIntegralCost(mergeDetail.getIntegral() - mergeDetail.getIntegralCost() + tempDetail.getIntegralCost());
                tempDetail.setDcwIntegralCost(mergeDetail.getDcwIntegral() - mergeDetail.getDcwIntegralCost() + tempDetail.getDcwIntegralCost());
                tempDetail.setDcwIntegralRawCost(mergeDetail.getDcwIntegralRaw() - mergeDetail.getDcwIntegralRawCost() + tempDetail.getDcwIntegralRawCost());
                IntegralCoefficientEntity ration = integralCoefficientMapper.findIntegralCoefficientByOrderTime(tempDetail.getIntegralGrantTime());
                if(ration == null){
                    tempDetail.setEmpiriValue(tempDetail.getIntegralCost() + tempDetail.getDcwIntegralRawCost());
                }else {
                    tempDetail.setEmpiriValue(Math.round(tempDetail.getIntegralCost() * ration.getIntegralRation() + tempDetail.getDcwIntegralRawCost() * ration.getDcwRation()));
                }
                return tempDetail;
            }
        });

        // 关联套餐明细DCW审核通过事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<>());
    }

    @Override
    public void orderCancel(EventBaseEntity event, JSONObject object) {
        geneDetailBaseEntityUtil.orderEventServiceHandler(event, object, new EventHandler<GeneDetailBaseEntity>() {

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempGeneDetail, GeneDetailBaseEntity mergeGeneDetail) {
                tempGeneDetail.setOrderIncome(0L - mergeGeneDetail.getOrderIncome() + tempGeneDetail.getOrderIncome());
                tempGeneDetail.setOrderCancelCount(mergeGeneDetail.getOrderPlaceCount());
                tempGeneDetail.setOrderCancelCost(mergeGeneDetail.getOrderPlaceCost());
                tempGeneDetail.setOrderCancelTime(mergeGeneDetail.getEventTime());
                return tempGeneDetail;
            }
        });
        // 关联套餐明细取消事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>() {

            @Override
            public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail) {
                tempDetail.setTestingItemOrderIncome(0L - mergeDetail.getTestingItemOrderIncome() + tempDetail.getTestingItemOrderIncome());
                tempDetail.setOrderCancelTime(mergeDetail.getEventTime());
                return tempDetail;
            }
        });
    }

    @Override
    public void orderRefunded(EventBaseEntity event, JSONObject object) {

        geneDetailBaseEntityUtil.orderEventServiceHandler(event, object, new EventHandler<GeneDetailBaseEntity>() {
            @Override
            public void perfectDataAtTheBeginning(GeneDetailBaseEntity messageDetail) {
                paymentInfoDaoImpl.addPayment(messageDetail,PaymentInfoDao.TYPE_REFUND);
            }

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempGeneDetail, GeneDetailBaseEntity mergeGeneDetail) {
                tempGeneDetail.setCashIncome(0L - mergeGeneDetail.getRefundTotal() + tempGeneDetail.getCashIncome());
                tempGeneDetail.setOrderRefundTime(mergeGeneDetail.getEventTime());
                return tempGeneDetail;
            }
        });
        // 关联套餐明细退款事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<>());
    }

    @Override
    public void orderUpdated(EventBaseEntity event, JSONObject object) {

        geneDetailBaseEntityUtil.orderEventServiceHandler(event, object, new EventHandler<GeneDetailBaseEntity>() {

            @Override
            public GeneDetailBaseEntity perfectDataInDatelyDetail(GeneDetailBaseEntity tempGeneDetail, GeneDetailBaseEntity mergeGeneDetail) {
                tempGeneDetail.setOrderIncome(mergeGeneDetail.getOrderPrice() - mergeGeneDetail.getOrderIncome() + tempGeneDetail.getOrderIncome());
                tempGeneDetail.setOrderPlaceCost(mergeGeneDetail.getOrderPrice() - mergeGeneDetail.getOrderPlaceCost() + tempGeneDetail.getOrderPlaceCost());
                tempGeneDetail.setOrderPlaceCount(1L - mergeGeneDetail.getOrderPlaceCount() + tempGeneDetail.getOrderPlaceCount());
                tempGeneDetail.setOrderUpdateTime(mergeGeneDetail.getEventTime());
                return tempGeneDetail;
            }
        });
        // 关联套餐明细更新事件
        testingItemDetailBaseEntityUtil.testingItemEventHandler(event, object, new EventHandler<TestingItemDetailBaseEntity>() {

            @Override
            public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail) {
                tempDetail.setTestingItemOrderIncome(mergeDetail.getTestingItemPrice() - mergeDetail.getTestingItemOrderIncome() + tempDetail.getTestingItemOrderIncome());
                tempDetail.setOrderUpdateTime(mergeDetail.getEventTime());
                return tempDetail;
            }
        });
    }

    @Override
    public void orderLogistics(EventBaseEntity event, JSONObject object) {
        OrderLogisticsEntity orderLogisticsEntity = new OrderLogisticsEntity();
        orderLogisticsEntity.setTag(event.getTag());
        orderLogisticsEntity.setMessageId(event.getMessageId());
        orderLogisticsEntity.setRecordId(event.getRecordId());
        orderLogisticsEntity.setEvent(event.getEvent());
        orderLogisticsEntity.setEventTime(event.getEventTime());
        orderLogisticsEntity.setOrderId(object.getString("orderId"));
        orderLogisticsEntity.setLogisticsType(object.getString("logisticsType"));
        orderLogisticsEntity.setExpressNumber(object.getJSONObject("detail").getString("expressNumber"));
        orderLogisticsEntity.setExpressCompanyName(object.getJSONObject("detail").getString("expressCompanyName"));
        orderLogisticsEntity.setExpressCompanyId(object.getJSONObject("detail").getString("expressCompanyId"));

        orderLogisticsMapper.addOrderLogistics(orderLogisticsEntity);
    }
}
