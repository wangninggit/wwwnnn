package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.bean.LogisticsPayoutEntity;
import com.viathink.core.business.gene.dao.*;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.LogisticsPayoutMapper;
import com.viathink.core.business.gene.service.LogisticsPayoutEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogisticsPayoutEventServiceImpl implements LogisticsPayoutEventService {

    private final LogisticsPayoutMapper logisticsPayoutMapper;
    private final RegionDao regionDao;
    private final ProvinceDao provinceDao;
    private final CityDao cityDao;
    private final CountyDao countyDao;
    private final LocalStaffDao localStaffDao;

    @Autowired
    public LogisticsPayoutEventServiceImpl(LogisticsPayoutMapper logisticsPayoutMapper, RegionDao regionDao, ProvinceDao provinceDao, CityDao cityDao, CountyDao countyDao, LocalStaffDao localStaffDao) {
        this.logisticsPayoutMapper = logisticsPayoutMapper;
        this.regionDao = regionDao;
        this.provinceDao = provinceDao;
        this.cityDao = cityDao;
        this.countyDao = countyDao;
        this.localStaffDao = localStaffDao;
    }

    @Override
    public void eventHandle(EventBaseEntity event, JSONObject object) {

        LogisticsPayoutEntity logisticsPayoutEntity = new LogisticsPayoutEntity();
        GeneDetailBaseEntity geneDetailEntity = object.toJavaObject(GeneDetailBaseEntity.class);
        LocalStaffLocationDto locationDto = object.getObject("localStaffLocation", LocalStaffLocationDto.class);

        regionDao.addOrUpdateRegionById(locationDto);
        provinceDao.addOrUpdateProvinceById(locationDto);
        cityDao.addOrUpdateCityById(locationDto);
        countyDao.addOrUpdateCountyById(locationDto);
        localStaffDao.addOrUpdateLocalStaffById(geneDetailEntity.getLocalStaffId(),geneDetailEntity.getLocalStaffJobNumber(),geneDetailEntity.getLocalStaffName(),locationDto);

        logisticsPayoutEntity.setTag(event.getTag());
        logisticsPayoutEntity.setMessageId(event.getMessageId());
        logisticsPayoutEntity.setRecordId(event.getRecordId());
        logisticsPayoutEntity.setDayStr(event.getDayStr());
        logisticsPayoutEntity.setMonthStr(event.getMonthStr());
        logisticsPayoutEntity.setYearStr(event.getYearStr());
        logisticsPayoutEntity.setEvent(event.getEvent());
        logisticsPayoutEntity.setEventTime(event.getEventTime());
        logisticsPayoutEntity.setTotal(object.getLong("total"));
        logisticsPayoutEntity.setLocalStaffId(object.getString("localStaffId"));
        logisticsPayoutEntity.setLocalStaffJobNumber(object.getString("localStaffJobNumber"));
        logisticsPayoutEntity.setLocalStaffName(object.getString("localStaffName"));
        logisticsPayoutEntity.setLocalStaffRegionId(locationDto.getRegionId());
        logisticsPayoutEntity.setLocalStaffRegionName(locationDto.getRegionName());
        logisticsPayoutEntity.setLocalStaffProvinceId(locationDto.getProvinceId());
        logisticsPayoutEntity.setLocalStaffProvinceName(locationDto.getProvinceName());
        logisticsPayoutEntity.setLocalStaffCityId(locationDto.getCityId());
        logisticsPayoutEntity.setLocalStaffCityName(locationDto.getCityName());
        logisticsPayoutEntity.setLocalStaffCountyId(locationDto.getCountyId());
        logisticsPayoutEntity.setLocalStaffCountyName(locationDto.getCountyName());

        logisticsPayoutMapper.addLogisticsPayout(logisticsPayoutEntity);
    }
}
