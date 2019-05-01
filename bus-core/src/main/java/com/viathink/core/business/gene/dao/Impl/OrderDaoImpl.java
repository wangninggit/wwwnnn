package com.viathink.core.business.gene.dao.Impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.DoctorEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.dao.*;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.dto.OrderDateDto;
import com.viathink.core.business.gene.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {
    private final CityDao cityDao;
    private final CountyDao countyDao;
    private final LocalStaffDao localStaffDao;
    private final ProvinceDao provinceDao;
    private final RegionDao regionDao;
    private final DoctorDao doctorDao;
    private final TestingItemDao testingItemDao;
    private final GeneDetailMapper geneDetailMapper;
    private final GeneDailyDetailMapper geneDailyDetailMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final GeneQuarterlyDetailMapper geneQuarterlyDetailMapper;
    private final GeneYearlyDetailMapper geneYearlyDetailMapper;
    private final GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper;
    private final TestingItemTabDao testingItemTabDao;

    @Autowired
    public OrderDaoImpl(LocalStaffDao localStaffDao, CityDao cityDao, GeneYearlyDetailMapper geneYearlyDetailMapper, CountyDao countyDao, ProvinceDao provinceDao, GeneHalfYearlyDetailMapper geneHalfYearlyDetailMapper, RegionDao regionDao, DoctorDao doctorDao, TestingItemDao testingItemDao, GeneDetailMapper geneDetailMapper, GeneDailyDetailMapper geneDailyDetailMapper, TestingItemTabDao testingItemTabDao, GeneMonthlyDetailMapper geneMonthlyDetailMapper, GeneQuarterlyDetailMapper geneQuarterlyDetailMapper) {
        this.localStaffDao = localStaffDao;
        this.cityDao = cityDao;
        this.geneYearlyDetailMapper = geneYearlyDetailMapper;
        this.countyDao = countyDao;
        this.provinceDao = provinceDao;
        this.geneHalfYearlyDetailMapper = geneHalfYearlyDetailMapper;
        this.regionDao = regionDao;
        this.doctorDao = doctorDao;
        this.testingItemDao = testingItemDao;
        this.geneDetailMapper = geneDetailMapper;
        this.geneDailyDetailMapper = geneDailyDetailMapper;
        this.testingItemTabDao = testingItemTabDao;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.geneQuarterlyDetailMapper = geneQuarterlyDetailMapper;
    }

    @Override
    public void addOrUpdateAttachedData(GeneDetailBaseEntity geneDetailEntity, LocalStaffLocationDto locationDto) {
        // 大区
        regionDao.addOrUpdateRegionById(locationDto);
        // 省
        provinceDao.addOrUpdateProvinceById(locationDto);
        // 市
        cityDao.addOrUpdateCityById(locationDto);
        // 区
        countyDao.addOrUpdateCountyById(locationDto);
        // 创建或更新地服信息
        localStaffDao.addOrUpdateLocalStaffById(geneDetailEntity.getLocalStaffId(),geneDetailEntity.getLocalStaffJobNumber(),geneDetailEntity.getLocalStaffName(), locationDto);
        // 更新医生
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(geneDetailEntity.getDoctorId());
        doctorEntity.setDoctorName(geneDetailEntity.getDoctorName());
        doctorEntity.setHospitalName(geneDetailEntity.getHospitalName());
        doctorEntity.setHospitalAddress(geneDetailEntity.getHospitalAddress());
        doctorDao.addOrUpdateDoctorById(doctorEntity);
        // 更新套餐信息表
        List<TestingItemEntity> itemList = geneDetailEntity.getTestings();
        testingItemTabDao.addOrUpdateTestingItem(itemList);
        // 更新套餐表
        testingItemDao.updateTestingItem(itemList, geneDetailEntity.getOrderId());
    }

    @Override
    public GeneDetailBaseEntity overrideGeneDetaillField(EventBaseEntity event, LocalStaffLocationDto locationDto, GeneDetailBaseEntity geneDetailEntity) {
        geneDetailEntity.setTag(event.getTag());
        geneDetailEntity.setMessageId(event.getMessageId());
        geneDetailEntity.setRecordId(event.getRecordId());
        geneDetailEntity.setDayStr(event.getDayStr());
        geneDetailEntity.setMonthStr(event.getMonthStr());
        geneDetailEntity.setQuarterStr(event.getQuarterStr());
        geneDetailEntity.setHalfYearStr(event.getHalfYearStr());
        geneDetailEntity.setYearStr(event.getYearStr());
        geneDetailEntity.setEvent(event.getEvent());
        geneDetailEntity.setEventTime(event.getEventTime());
        // 更新地理位置
        geneDetailEntity.setLocalStaffRegionId(locationDto.getRegionId());
        geneDetailEntity.setLocalStaffRegionName(locationDto.getRegionName());
        geneDetailEntity.setLocalStaffProvinceId(locationDto.getProvinceId());
        geneDetailEntity.setLocalStaffProvinceName(locationDto.getProvinceName());
        geneDetailEntity.setLocalStaffCityId(locationDto.getCityId());
        geneDetailEntity.setLocalStaffCityName(locationDto.getCityName());
        geneDetailEntity.setLocalStaffCountyId(locationDto.getCountyId());
        geneDetailEntity.setLocalStaffCountyName(locationDto.getCountyName());
        return geneDetailEntity;
    }
    @Override
    public void orderFlowHandle(EventBaseEntity event, LocalStaffLocationDto locationDto, GeneDetailBaseEntity geneDetailEntity, GeneDetailBaseEntity oldGeneDetail,OrderDateDto orderDateDto) {
        // 更新event相关字段
        geneDetailEntity = this.overrideGeneDetaillField(event, locationDto, geneDetailEntity);
        // 更新附属信息
        this.addOrUpdateAttachedData(geneDetailEntity, locationDto);
        // 如果没有创建新的
        if (oldGeneDetail == null) {
            geneDetailMapper.addGeneDetail(geneDetailEntity);
            geneDailyDetailMapper.addGeneDailyDetail(geneDetailEntity);
            geneMonthlyDetailMapper.addGeneMonthlyDetail(geneDetailEntity);
            geneQuarterlyDetailMapper.addGeneQuarterlyDetail(geneDetailEntity);
            geneHalfYearlyDetailMapper.addGeneHalfYearlyDetail(geneDetailEntity);
            geneYearlyDetailMapper.addGeneYearlyDetail(geneDetailEntity);
        } else {
            GeneDetailBaseEntity dayDetail = orderDateDto.getDay();
            dayDetail = this.copyBaseField(dayDetail,geneDetailEntity);
            GeneDetailBaseEntity monthDetail = orderDateDto.getMonth();
            monthDetail = this.copyBaseField(monthDetail,geneDetailEntity);
            GeneDetailBaseEntity quarterDetail = orderDateDto.getQuarter();
            quarterDetail = this.copyBaseField(quarterDetail,geneDetailEntity);
            GeneDetailBaseEntity halfYearDetail = orderDateDto.getHalfYear();
            halfYearDetail = this.copyBaseField(halfYearDetail,geneDetailEntity);
            GeneDetailBaseEntity yearDetail = orderDateDto.getYear();
            yearDetail = this.copyBaseField(yearDetail,geneDetailEntity);

            // 判断是否跨日
            if (oldGeneDetail.getDayStr().equals(geneDetailEntity.getDayStr())) {
                // 在同一天，更新
                geneDailyDetailMapper.updateGeneDailyDetailByDayStrAndOrderId(dayDetail);
            } else {
                geneDailyDetailMapper.addGeneDailyDetail(dayDetail);
            }
            if (oldGeneDetail.getMonthStr().equals(geneDetailEntity.getMonthStr())) {
                // 在同一月，更新
                geneMonthlyDetailMapper.updateGeneMonthlyDetailByOrderIdAndMonthStr(monthDetail);
            } else {
                geneMonthlyDetailMapper.addGeneMonthlyDetail(monthDetail);
            }
            if (oldGeneDetail.getQuarterStr().equals(geneDetailEntity.getQuarterStr())) {
                // 在同一季度，更新
                geneQuarterlyDetailMapper.updateGeneQuarterlyDetailByOrderIdAndQuarterStr(quarterDetail);
            } else {
                geneQuarterlyDetailMapper.addGeneQuarterlyDetail(quarterDetail);
            }
            if (oldGeneDetail.getHalfYearStr().equals(geneDetailEntity.getHalfYearStr())) {
                // 在同一半年，更新
                geneHalfYearlyDetailMapper.updateGeneHalfYearlyDetailByOrderIdAndHalfYearStr(halfYearDetail);
            } else {
                geneHalfYearlyDetailMapper.addGeneHalfYearlyDetail(halfYearDetail);
            }
            if (oldGeneDetail.getYearStr().equals(geneDetailEntity.getYearStr())) {
                // 在同一年，更新
                geneYearlyDetailMapper.updateGeneYearlyDetailByOrderIdAndYearStr(yearDetail);
            } else {
                geneYearlyDetailMapper.addGeneYearlyDetail(yearDetail);
            }
            oldGeneDetail = this.copyBaseField(oldGeneDetail,geneDetailEntity);

            geneDetailMapper.updateGeneDetailByOrderId(oldGeneDetail);
        }
    }

    @Override
    public GeneDetailBaseEntity copyBaseField(GeneDetailBaseEntity source, GeneDetailBaseEntity target){
        if(target == null)
            return null;
        target = JSONObject.parseObject(JSONObject.toJSONString(target), GeneDetailBaseEntity.class);
        target.setOrderIncome(source.getOrderIncome());
        target.setCashIncome(source.getCashIncome());
        target.setFinanceConfirmIncome(source.getFinanceConfirmIncome());
        target.setTestingItemCost(source.getTestingItemCost());
        target.setTestingItemConfirmCost(source.getTestingItemConfirmCost());
        target.setIntegralCost(source.getIntegralCost());
        target.setOrderCreateTime(source.getOrderCreateTime());
        target.setOrderPayTime(source.getOrderPayTime());
        target.setOrderCancelTime(source.getOrderCancelTime());
        target.setSampleConfirmTime(source.getSampleConfirmTime());
        target.setTestingReportUploadTime(source.getTestingReportUploadTime());
        target.setIntegralGrantTime(source.getIntegralGrantTime());
        target.setOrderInvoiceTime(source.getOrderInvoiceTime());
        target.setOrderRefundTime(source.getOrderRefundTime());
        target.setOrderUpdateTime(source.getOrderUpdateTime());
        target.setOrderPlaceCost(source.getOrderPlaceCost());
        target.setOrderPlaceCount(source.getOrderPlaceCount());
        target.setOrderCancelCost(source.getOrderCancelCost());
        target.setOrderCancelCount(source.getOrderCancelCount());
        return target;
    }
}
