package com.viathink.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.TestingItemDetailBaseEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestingItemDetailBaseEntityUtil {
    private Logger logger = LoggerFactory.getLogger(TestingItemDetailBaseEntityUtil.class);

    private final TestingItemDetailMapper testingItemDetailMapper;
    private final TestingItemDailyDetailMapper testingItemDailyDetailMapper;
    private final TestingItemMonthlyDetailMapper testingItemMonthlyDetailMapper;
    private final TestingItemQuarterlyDetailMapper testingItemQuarterlyDetailMapper;
    private final TestingItemHalfYearlyDetailMapper testingItemHalfYearlyDetailMapper;
    private final TestingItemYearlyDetailMapper testingItemYearlyDetailMapper;

    @Autowired
    public TestingItemDetailBaseEntityUtil(TestingItemDetailMapper testingItemDetailMapper, TestingItemDailyDetailMapper testingItemDailyDetailMapper, TestingItemMonthlyDetailMapper testingItemMonthlyDetailMapper, TestingItemQuarterlyDetailMapper testingItemQuarterlyDetailMapper, TestingItemHalfYearlyDetailMapper testingItemHalfYearlyDetailMapper, TestingItemYearlyDetailMapper testingItemYearlyDetailMapper) {
        this.testingItemDetailMapper = testingItemDetailMapper;
        this.testingItemDailyDetailMapper = testingItemDailyDetailMapper;
        this.testingItemMonthlyDetailMapper = testingItemMonthlyDetailMapper;
        this.testingItemQuarterlyDetailMapper = testingItemQuarterlyDetailMapper;
        this.testingItemHalfYearlyDetailMapper = testingItemHalfYearlyDetailMapper;
        this.testingItemYearlyDetailMapper = testingItemYearlyDetailMapper;
    }

    private static TestingItemDetailBaseEntity copyTestingItemDetailBaseEntityAndModifyMainFieldByDatelyEntity(TestingItemDetailBaseEntity copyEntity, TestingItemDetailBaseEntity sourceEntuty){
        TestingItemDetailBaseEntity tempTestingItemDetail = JSONObject.parseObject(JSONObject.toJSONString(copyEntity), TestingItemDetailBaseEntity.class);

        if(sourceEntuty != null) {
            tempTestingItemDetail.setTestingItemRecordCost(sourceEntuty.getTestingItemRecordCost());
            tempTestingItemDetail.setTestingItemConfirmRecordCost(sourceEntuty.getTestingItemConfirmRecordCost());
            tempTestingItemDetail.setTestingItemOrderIncome(sourceEntuty.getTestingItemOrderIncome());
            tempTestingItemDetail.setTestingItemCashIncome(sourceEntuty.getTestingItemCashIncome());
            tempTestingItemDetail.setTestingItemFinanceConfirmIncome(sourceEntuty.getTestingItemFinanceConfirmIncome());
            tempTestingItemDetail.setTestingItemPlaceCount(sourceEntuty.getTestingItemPlaceCount());
            tempTestingItemDetail.setTestingItemCancelCount(sourceEntuty.getTestingItemCancelCount());
        }else{
            tempTestingItemDetail.setTestingItemRecordCost(0L);
            tempTestingItemDetail.setTestingItemConfirmRecordCost(0L);
            tempTestingItemDetail.setTestingItemOrderIncome(0L);
            tempTestingItemDetail.setTestingItemCashIncome(0L);
            tempTestingItemDetail.setTestingItemFinanceConfirmIncome(0L);
            tempTestingItemDetail.setTestingItemPlaceCount(0L);
            tempTestingItemDetail.setTestingItemCancelCount(0L);
        }
        return tempTestingItemDetail;
    }

    private TestingItemDetailBaseEntity overrideTestingItemDetaillFields(EventBaseEntity event,TestingItemDetailBaseEntity entity){
        entity.setTag(event.getTag());
        entity.setMessageId(event.getMessageId());
        entity.setRecordId(event.getRecordId());
        entity.setDayStr(event.getDayStr());
        entity.setMonthStr(event.getMonthStr());
        entity.setQuarterStr(event.getQuarterStr());
        entity.setHalfYearStr(event.getHalfYearStr());
        entity.setYearStr(event.getYearStr());
        entity.setEvent(event.getEvent());
        entity.setEventTime(event.getEventTime());
        return entity;
    }

    private TestingItemDetailBaseEntity copyBaseField(TestingItemDetailBaseEntity source,TestingItemDetailBaseEntity target){

        target.setTestingItemOrderIncome(source.getTestingItemOrderIncome());
        target.setTestingItemCashIncome(source.getTestingItemCashIncome());
        target.setTestingItemFinanceConfirmIncome(source.getTestingItemFinanceConfirmIncome());
        target.setTestingItemRecordCost(source.getTestingItemRecordCost());
        target.setTestingItemConfirmRecordCost(source.getTestingItemConfirmRecordCost());
        target.setOrderCreateTime(source.getOrderCreateTime());
        target.setOrderPayTime(source.getOrderPayTime());
        target.setOrderCancelTime(source.getOrderCancelTime());
        target.setSampleConfirmTime(source.getSampleConfirmTime());
        target.setOrderInvoiceTime(source.getOrderInvoiceTime());
        target.setTestingReportUploadTime(source.getTestingReportUploadTime());
        target.setOrderUpdateTime(source.getOrderUpdateTime());
        target.setTestingItemCancelCount(source.getTestingItemCancelCount());
        target.setTestingItemPlaceCount(source.getTestingItemPlaceCount());
        return target;
    }

    private void perfectDataInTestingItemDatelyDetailTable(Object mapper,TestingItemDetailBaseEntity mergeDetail ,EventHandler<TestingItemDetailBaseEntity> eventHandler) {
        TestingItemDetailBaseEntity testingItemDatelyDetail;
        TestingItemDetailBaseEntity tempTestingItemDetail;
        if(mapper == testingItemDetailMapper){
            testingItemDatelyDetail = mergeDetail;
            tempTestingItemDetail = mergeDetail;
        }else{
            testingItemDatelyDetail = ((TestingItemDatelyDetailBaseMapper)mapper).findTestingItemDetailByOrderIdAndTestingItemIdAndDatelyStr(mergeDetail);
            tempTestingItemDetail = TestingItemDetailBaseEntityUtil.copyTestingItemDetailBaseEntityAndModifyMainFieldByDatelyEntity(mergeDetail,testingItemDatelyDetail);
        }
        tempTestingItemDetail = eventHandler.perfectDataInDatelyDetail(tempTestingItemDetail,mergeDetail);
        if (testingItemDatelyDetail != null) {
            if(mapper == testingItemDetailMapper){
                ((TestingItemDetailMapper)mapper).updateTestingItemDetailByOrderIdAndTestingItemId(tempTestingItemDetail);
            }else{
                ((TestingItemDatelyDetailBaseMapper)mapper).updateTestingItemDetailByOrderIdAndTestingItemIdAndDatelyStr(tempTestingItemDetail);
            }
        } else {
            ((TestingItemDatelyDetailBaseMapper)mapper).addTestingItemDetail(tempTestingItemDetail);
        }
    }

    public void testingItemEventHandler(EventBaseEntity event, JSONObject object, EventHandler<TestingItemDetailBaseEntity> eventHandler) {
        GeneDetailBaseEntity geneDetailBaseEntity = object.toJavaObject(GeneDetailBaseEntity.class);
        List<TestingItemDetailBaseEntity> messageTestingItemDetailList = new ArrayList<>();
        if(geneDetailBaseEntity.getTestings() != null) {
            for (TestingItemEntity testingItemEntity : geneDetailBaseEntity.getTestings()) {
                TestingItemDetailBaseEntity entity = new TestingItemDetailBaseEntity(testingItemEntity, geneDetailBaseEntity.getOrderId());
                messageTestingItemDetailList.add(entity);
            }
        }
        List<TestingItemDetailBaseEntity> oldTestingItemDetailList = testingItemDetailMapper.getTestingItemDetailsByOrderId(geneDetailBaseEntity.getOrderId());
        // 逐条遍历message中的记录
        for(TestingItemDetailBaseEntity messageTestingItemDetail : messageTestingItemDetailList){
            // 更新附属信息
            messageTestingItemDetail = this.overrideTestingItemDetaillFields(event, messageTestingItemDetail);
            // 从老的list中取出对应的记录
            TestingItemDetailBaseEntity oldTestingItemDetail = null;
            if(oldTestingItemDetailList != null){
                for(TestingItemDetailBaseEntity entity : oldTestingItemDetailList){
                    if(messageTestingItemDetail.getTestingItemId().equals(entity.getTestingItemId()))
                        oldTestingItemDetail = entity;
                }
            }

            eventHandler.perfectDataAtTheBeginning(messageTestingItemDetail);

            if(oldTestingItemDetail == null){
                messageTestingItemDetail.setTestingItemPlaceCount(1L);
                messageTestingItemDetail = eventHandler.perfectDataWhenOldDetailIsNull(messageTestingItemDetail);

                testingItemDetailMapper.addTestingItemDetail(messageTestingItemDetail);
                testingItemDailyDetailMapper.addTestingItemDetail(messageTestingItemDetail);
                testingItemMonthlyDetailMapper.addTestingItemDetail(messageTestingItemDetail);
                testingItemQuarterlyDetailMapper.addTestingItemDetail(messageTestingItemDetail);
                testingItemHalfYearlyDetailMapper.addTestingItemDetail(messageTestingItemDetail);
                testingItemYearlyDetailMapper.addTestingItemDetail(messageTestingItemDetail);
            }else{
                // 更新附属信息
                TestingItemDetailBaseEntity mergeDetail = this.copyBaseField(oldTestingItemDetail,messageTestingItemDetail);
                // 更新日、月、年。。。表中数据
                perfectDataInTestingItemDatelyDetailTable(testingItemDailyDetailMapper,mergeDetail,eventHandler);
                perfectDataInTestingItemDatelyDetailTable(testingItemMonthlyDetailMapper,mergeDetail,eventHandler);
                perfectDataInTestingItemDatelyDetailTable(testingItemQuarterlyDetailMapper,mergeDetail,eventHandler);
                perfectDataInTestingItemDatelyDetailTable(testingItemHalfYearlyDetailMapper,mergeDetail,eventHandler);
                perfectDataInTestingItemDatelyDetailTable(testingItemYearlyDetailMapper,mergeDetail,eventHandler);
                perfectDataInTestingItemDatelyDetailTable(testingItemDetailMapper,mergeDetail,eventHandler);

                // 从oldTestingItemDetailList中删除已经处理过的记录，剩下的记录就是被取消的套餐，在下面处理
                oldTestingItemDetailList.remove(oldTestingItemDetail);
            }
        }

        if((oldTestingItemDetailList != null) && (!oldTestingItemDetailList.isEmpty())){
            for(TestingItemDetailBaseEntity oldTestingItemDetail : oldTestingItemDetailList){

                oldTestingItemDetail = this.overrideTestingItemDetaillFields(event,oldTestingItemDetail);

                EventHandler<TestingItemDetailBaseEntity> handler = new EventHandler<TestingItemDetailBaseEntity>() {

                    @Override
                    public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail ) {
                        tempDetail.setTestingItemOrderIncome(0L - mergeDetail.getTestingItemOrderIncome() + tempDetail.getTestingItemOrderIncome());
                        tempDetail.setTestingItemCashIncome(0L - mergeDetail.getTestingItemCashIncome() + tempDetail.getTestingItemCashIncome());
                        tempDetail.setTestingItemFinanceConfirmIncome(0L - mergeDetail.getTestingItemFinanceConfirmIncome() + tempDetail.getTestingItemFinanceConfirmIncome());
                        tempDetail.setTestingItemCancelCount(1L);
                        return tempDetail;
                    }
                };

                perfectDataInTestingItemDatelyDetailTable(testingItemDailyDetailMapper,oldTestingItemDetail,handler);
                perfectDataInTestingItemDatelyDetailTable(testingItemMonthlyDetailMapper,oldTestingItemDetail,handler);
                perfectDataInTestingItemDatelyDetailTable(testingItemQuarterlyDetailMapper,oldTestingItemDetail,handler);
                perfectDataInTestingItemDatelyDetailTable(testingItemHalfYearlyDetailMapper,oldTestingItemDetail,handler);
                perfectDataInTestingItemDatelyDetailTable(testingItemYearlyDetailMapper,oldTestingItemDetail,handler);
                perfectDataInTestingItemDatelyDetailTable(testingItemDetailMapper,oldTestingItemDetail,handler);
            }
        }
    }

    public void testingItemInvoiced(EventBaseEntity event, JSONObject object){
        JSONArray orderIds = object.getJSONArray("orderIds");
        for(Object obj : orderIds){
            String orderId = obj.toString();
            // 先查找老的
            List<TestingItemDetailBaseEntity> oldTestingItemDetailList = testingItemDetailMapper.getTestingItemDetailsByOrderId(orderId);
            if(oldTestingItemDetailList == null){
                logger.error("检测项目开票事件： 检测项目明细表中没有找到订单对应的检测项目记录");
            }else{
                for(TestingItemDetailBaseEntity oldTestingItemDetail : oldTestingItemDetailList){
                    oldTestingItemDetail = this.overrideTestingItemDetaillFields(event,oldTestingItemDetail);

                    EventHandler<TestingItemDetailBaseEntity> handler = new EventHandler<TestingItemDetailBaseEntity>() {

                        @Override
                        public TestingItemDetailBaseEntity perfectDataInDatelyDetail(TestingItemDetailBaseEntity tempDetail, TestingItemDetailBaseEntity mergeDetail) {
                            tempDetail.setTestingItemFinanceConfirmIncome(mergeDetail.getTestingItemCashIncome() - mergeDetail.getTestingItemFinanceConfirmIncome() + tempDetail.getTestingItemFinanceConfirmIncome());
                            tempDetail.setTestingItemConfirmRecordCost(mergeDetail.getTestingItemRecordCost());
                            tempDetail.setOrderInvoiceTime(mergeDetail.getEventTime());
                            return tempDetail;
                        }
                    };

                    perfectDataInTestingItemDatelyDetailTable(testingItemDailyDetailMapper,oldTestingItemDetail,handler);
                    perfectDataInTestingItemDatelyDetailTable(testingItemMonthlyDetailMapper,oldTestingItemDetail,handler);
                    perfectDataInTestingItemDatelyDetailTable(testingItemQuarterlyDetailMapper,oldTestingItemDetail,handler);
                    perfectDataInTestingItemDatelyDetailTable(testingItemHalfYearlyDetailMapper,oldTestingItemDetail,handler);
                    perfectDataInTestingItemDatelyDetailTable(testingItemYearlyDetailMapper,oldTestingItemDetail,handler);
                    perfectDataInTestingItemDatelyDetailTable(testingItemDetailMapper,oldTestingItemDetail,handler);
                }
            }
        }
    }
}
