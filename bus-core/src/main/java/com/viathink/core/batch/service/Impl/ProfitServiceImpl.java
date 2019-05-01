package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.dto.ProfitReportDto;
import com.viathink.core.batch.dto.ProfitResultDto;
import com.viathink.core.batch.service.ProfitService;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.GeneHalfYearlyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneMonthlyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneYearlyDetailMapper;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProfitServiceImpl implements ProfitService {
    private final SnapshootDao snapshootDao;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper;
    private final GeneYearlyDetailMapper geneYearlyDetailMapper;

    @Autowired
    public ProfitServiceImpl(SnapshootDao snapshootDao, GeneMonthlyDetailMapper geneMonthlyDetailMapper, GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper, GeneYearlyDetailMapper geneYearlyDetailMapper) {
        this.snapshootDao = snapshootDao;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.geneHalfYearlyDetailMapper = geneHalfYearlyDetailMapper;
        this.geneYearlyDetailMapper = geneYearlyDetailMapper;
    }

    @Override
    public void getMonthDetailProfitReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String monthStr = businessDate.getMonthStr();
        List<ProfitReportDto> list = geneMonthlyDetailMapper.getProfitByMonthStr(monthStr);
        ProfitReportDto profitReportDto = geneMonthlyDetailMapper.countProfitByMonthStr(monthStr);
        ProfitResultDto profitResultDto = new ProfitResultDto();
        profitResultDto.setList(list);
        profitResultDto.setSum(list.size() == 0 ? null : profitReportDto);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROFIT);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity.setRecord(JSON.toJSONString(profitResultDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);

    }

    @Override
    public void getHalfYearDetailProfitReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String halfYearStr = businessDate.getHalfYearStr();
        List<ProfitReportDto> list = geneHalfYearlyDetailMapper.getProfitByHalfYearStr(halfYearStr);
        ProfitReportDto profitReportDto = geneHalfYearlyDetailMapper.countProfitByHalfYearStr(halfYearStr);
        ProfitResultDto profitResultDto = new ProfitResultDto();
        profitResultDto.setList(list);
        profitResultDto.setSum(list.size() == 0 ? null : profitReportDto);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROFIT);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity.setRecord(JSON.toJSONString(profitResultDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);

    }

    @Override
    public void getYearDetailProfitReport(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        String yearStr = businessDate.getYearStr();
        List<ProfitReportDto> list = geneYearlyDetailMapper.getProfitByYearStr(yearStr);
        ProfitReportDto profitReportDto = geneYearlyDetailMapper.countProfitByYearStr(yearStr);
        ProfitResultDto profitResultDto = new ProfitResultDto();
        profitResultDto.setList(list);
        profitResultDto.setSum(list.size() == 0 ? null : profitReportDto);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROFIT);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        snapshootEntity.setRecord(JSON.toJSONString(profitResultDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);

    }
}
