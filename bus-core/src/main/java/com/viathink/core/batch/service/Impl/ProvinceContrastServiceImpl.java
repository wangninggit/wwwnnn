package com.viathink.core.batch.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.core.batch.dao.SnapshootDao;
import com.viathink.core.batch.dto.ProvinceSnapshootRecordDto;
import com.viathink.core.batch.dto.RecordObjectDto;
import com.viathink.core.batch.service.ContrastService;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.mapper.GeneDailyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneHalfYearlyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneMonthlyDetailMapper;
import com.viathink.core.business.gene.mapper.GeneYearlyDetailMapper;
import com.viathink.core.util.BusinessDate;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.BatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.viathink.core.util.BatchUtil.sumPackRecordData;

/*
    指定使用哪个实现类自动注入
    @Autowired
    @Qualifier("videoB")    //值为实现类名的首字母小写
    private ContrastService contrastService;
 */
@Service
public class ProvinceContrastServiceImpl implements ContrastService {
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper;
    private final GeneYearlyDetailMapper geneYearlyDetailMapper;
    private final SnapshootDao snapshootDao;

    @Autowired
    public ProvinceContrastServiceImpl(GeneDailyDetailMapper geneDailyDetailMapper, GeneMonthlyDetailMapper geneMonthlyDetailMapper, GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper, GeneYearlyDetailMapper geneYearlyDetailMapper, SnapshootDao snapshootDao) {
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.geneHalfYearlyDetailMapper = geneHalfYearlyDetailMapper;
        this.geneYearlyDetailMapper = geneYearlyDetailMapper;
        this.snapshootDao = snapshootDao;
    }

    @Override
    public void dailySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<ProvinceSnapshootRecordDto> list = geneDailyDetailMapper.getSnapshootRecordByDailyStrAndGroupByProvinceId(businessDate.getDayStr());
        RecordObjectDto recordObjectDto = provincePackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_DAY_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void monthlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<ProvinceSnapshootRecordDto> list = geneMonthlyDetailMapper.getSnapshootRecordByMonthlyStrAndGroupByProvinceId(businessDate.getMonthStr());
        RecordObjectDto recordObjectDto = provincePackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_MONTH_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void halfYearlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<ProvinceSnapshootRecordDto> list = geneHalfYearlyDetailMapper.getSnapshootRecordByHalfYearlyStrAndGroupByProvinceId(businessDate.getHalfYearStr());
        RecordObjectDto recordObjectDto = provincePackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_HALF_YEAR_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    @Override
    public void yearlySnapshoot(Date date) {
        BusinessDate businessDate = BusinessDateUtil.getBusinessDate(date.getTime());
        List<ProvinceSnapshootRecordDto> list = geneYearlyDetailMapper.getSnapshootRecordByYearlyStrAndGroupByProvinceId(businessDate.getYearStr());
        RecordObjectDto recordObjectDto = provincePackRecordData(list);

        SnapshootEntity snapshootEntity = new SnapshootEntity();
        snapshootEntity.setType(BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        snapshootEntity.setTimeRange(BatchUtil.TIME_RANGE_YEAR_STR);
        snapshootEntity = BatchUtil.setDatelyStr(snapshootEntity,businessDate);
        snapshootEntity.setRecord(JSONObject.toJSONString(recordObjectDto,SerializerFeature.WriteMapNullValue));
        snapshootDao.addOrUpdateSnapshootById(snapshootEntity);
    }

    public static RecordObjectDto provincePackRecordData(List<ProvinceSnapshootRecordDto> list){
        RecordObjectDto recordObjectDto = new RecordObjectDto();
        recordObjectDto.setList(list);
        if(list.size() > 0) {
            ProvinceSnapshootRecordDto sum = new ProvinceSnapshootRecordDto();
            sum.setProvinceId("sum");
            sum.setProvinceName("总计");
            recordObjectDto.setSum(sumPackRecordData(list,sum));
        }
        return recordObjectDto;
    }
}
