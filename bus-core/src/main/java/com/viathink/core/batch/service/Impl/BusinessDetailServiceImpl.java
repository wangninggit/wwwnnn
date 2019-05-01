package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.dto.BusinessDetailResult;
import com.viathink.core.batch.dto.BusinessResult;
import com.viathink.core.batch.service.BusinessDetailService;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.DetailResultDto;
import com.viathink.core.business.gene.mapper.GeneDailyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneMonthlyDetailMapper;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BusinessDetailServiceImpl implements BusinessDetailService {

    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final SnapshootDao snapshootDao;

    @Autowired
    public BusinessDetailServiceImpl(GeneDailyDetailMapper geneDailyDetailMapper, GeneMonthlyDetailMapper geneMonthlyDetailMapper, SnapshootDao snapshootDao) {
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.snapshootDao = snapshootDao;
    }

    @Override
    public void getDailyDetailReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String dayStr = businessDate.getDayStr();
        BusinessDetailResult businessDetailResult = geneDailyDetailMapper.countDailyDetailReportByDayStr(dayStr);
        List<DetailResultDto> geneDetailBaseEntities = geneDailyDetailMapper.getDailyDetailReportByDayStr(dayStr);
        Map<String, Object> map = new HashMap<>();
        map.put("list", geneDetailBaseEntities);
        map.put("sum",geneDetailBaseEntities.size() == 0 ? null : businessDetailResult);
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        snapshootEntity.setRecord(JSON.toJSONString(map,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void getMonthDetailReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String monthStr = businessDate.getMonthStr();
        BusinessDetailResult businessDetailResult = geneDailyDetailMapper.countMonthDetailReportByMonthStr(monthStr);
        List<BusinessDetailResult> list =  geneDailyDetailMapper.getMonthDetailReportGroupByDayStr(monthStr);
        BusinessResult businessResult = new BusinessResult();
        businessResult.setSum(list.size() == 0 ? null : businessDetailResult);
        businessResult.setList(list);
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setRecord(JSON.toJSONString(businessResult,SerializerFeature.WriteMapNullValue));

        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void getHalfYearDetailReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String halfYearStr = businessDate.getHalfYearStr();
        BusinessDetailResult businessDetailResult = geneMonthlyDetailMapper.countHalfYearDetailReportByHalfYearStr(halfYearStr);
        List<BusinessDetailResult> list =  geneMonthlyDetailMapper.getHalfYearDetailReportGroupByMonthStr(halfYearStr);
        BusinessResult businessResult = new BusinessResult();
        businessResult.setSum(list.size() == 0 ? null : businessDetailResult);
        businessResult.setList(list);
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity.setRecord(JSON.toJSONString(businessResult,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void getYearDetailReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String yearStr = businessDate.getYearStr();
        BusinessDetailResult businessDetailResult = geneMonthlyDetailMapper.countYearDetailReportByYearStr(yearStr);
        List<BusinessDetailResult> list =  geneMonthlyDetailMapper.getYearDetailReportGroupByMonthStr(yearStr);
        BusinessResult businessResult = new BusinessResult();
        businessResult.setSum(list.size() == 0 ? null : businessDetailResult);
        businessResult.setList(list);
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        snapshootEntity.setRecord(JSON.toJSONString(businessResult,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }
}
