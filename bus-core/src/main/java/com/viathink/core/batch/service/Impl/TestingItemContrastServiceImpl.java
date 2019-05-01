package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.dto.RecordObjectDto;
import com.viathink.core.batch.dto.TestingItemSnapshootRecordDto;
import com.viathink.core.batch.service.ContrastService;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.TestingItemDailyDetailMapper;
import com.viathink.core.business.gene.mapper.TestingItemHalfYearlyDetailMapper;
import com.viathink.core.business.gene.mapper.TestingItemMonthlyDetailMapper;
import com.viathink.core.business.gene.mapper.TestingItemYearlyDetailMapper;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
    指定使用哪个实现类自动注入
    @Autowired
    @Qualifier("videoB")    //值为实现类名的首字母小写
    private ContrastService contrastService;
 */
@Service
public class TestingItemContrastServiceImpl implements ContrastService {
    private final TestingItemDailyDetailMapper testingItemDailyDetailMapper;
    private final TestingItemMonthlyDetailMapper testingItemMonthlyDetailMapper;
    private final TestingItemHalfYearlyDetailMapper testingItemHalfYearlyDetailMapper;
    private final TestingItemYearlyDetailMapper testingItemYearlyDetailMapper;
    private final SnapshootDao snapshootDao;

    @Autowired
    public TestingItemContrastServiceImpl(TestingItemDailyDetailMapper testingItemDailyDetailMapper, TestingItemMonthlyDetailMapper testingItemMonthlyDetailMapper, TestingItemHalfYearlyDetailMapper testingItemHalfYearlyDetailMapper, TestingItemYearlyDetailMapper testingItemYearlyDetailMapper, SnapshootDao snapshootDao) {
        this.testingItemDailyDetailMapper = testingItemDailyDetailMapper;
        this.testingItemMonthlyDetailMapper = testingItemMonthlyDetailMapper;
        this.testingItemHalfYearlyDetailMapper = testingItemHalfYearlyDetailMapper;
        this.testingItemYearlyDetailMapper = testingItemYearlyDetailMapper;
        this.snapshootDao = snapshootDao;
    }

    @Override
    public void dailySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<TestingItemSnapshootRecordDto> list = testingItemDailyDetailMapper.getSnapshootRecordByDatelyStrAndGroupByTestingItemId(businessDate.getDayStr());
        RecordObjectDto recordObjectDto = testingItemPackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void monthlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<TestingItemSnapshootRecordDto> list = testingItemMonthlyDetailMapper.getSnapshootRecordByDatelyStrAndGroupByTestingItemId(businessDate.getMonthStr());
        RecordObjectDto recordObjectDto = testingItemPackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void halfYearlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<TestingItemSnapshootRecordDto> list = testingItemHalfYearlyDetailMapper.getSnapshootRecordByDatelyStrAndGroupByTestingItemId(businessDate.getHalfYearStr());
        RecordObjectDto recordObjectDto = testingItemPackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void yearlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<TestingItemSnapshootRecordDto> list = testingItemYearlyDetailMapper.getSnapshootRecordByDatelyStrAndGroupByTestingItemId(businessDate.getYearStr());
        RecordObjectDto recordObjectDto = testingItemPackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    public static RecordObjectDto testingItemPackRecordData(List<TestingItemSnapshootRecordDto> list){
        RecordObjectDto recordObjectDto = new RecordObjectDto();
        recordObjectDto.setList(list);
        if(list.size() > 0) {
            TestingItemSnapshootRecordDto sum = new TestingItemSnapshootRecordDto();
            sum.setTestingItemId("sum");
            sum.setTestingItem("总计");
            for (TestingItemSnapshootRecordDto item : list) {
                sum.setTestingItemOrderIncome(sum.getTestingItemOrderIncome() + item.getTestingItemOrderIncome());
                sum.setTestingItemCashIncome(sum.getTestingItemCashIncome() + item.getTestingItemCashIncome());
                sum.setTestingItemFinanceConfirmIncome(sum.getTestingItemFinanceConfirmIncome() + item.getTestingItemFinanceConfirmIncome());
                sum.setTestingItemRecordCost(sum.getTestingItemRecordCost() + item.getTestingItemRecordCost());
                sum.setTestingItemConfirmRecordCost(sum.getTestingItemConfirmRecordCost() + item.getTestingItemConfirmRecordCost());
                sum.setTestingItemCancelCount(sum.getTestingItemCancelCount() + item.getTestingItemCancelCount());
                sum.setTestingItemPlaceCount(sum.getTestingItemPlaceCount() + item.getTestingItemPlaceCount());
            }
            recordObjectDto.setSum(sum);
        }
        return recordObjectDto;
    }
}
