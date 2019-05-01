package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.LocalStaffEntity;
import com.viathink.core.business.gene.dao.CityDao;
import com.viathink.core.business.gene.dao.CountyDao;
import com.viathink.core.business.gene.dao.ProvinceDao;
import com.viathink.core.business.gene.dao.RegionDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.LocalStaffMapper;
import com.viathink.core.business.gene.service.LocalStaffEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalStaffEventServiceImpl implements LocalStaffEventService {

    private final LocalStaffMapper localStaffMapper;
    private final RegionDao regionDao;
    private final ProvinceDao provinceDao;
    private final CityDao cityDao;
    private final CountyDao countyDao;

    @Autowired
    public LocalStaffEventServiceImpl(LocalStaffMapper localStaffMapper, RegionDao regionDao, ProvinceDao provinceDao, CityDao cityDao, CountyDao countyDao) {
        this.localStaffMapper = localStaffMapper;
        this.regionDao = regionDao;
        this.provinceDao = provinceDao;
        this.cityDao = cityDao;
        this.countyDao = countyDao;
    }

    @Override
    public void eventHandle(EventBaseEntity event, JSONObject object) {

        LocalStaffLocationDto locationDto = object.getObject("localStaffLocation", LocalStaffLocationDto.class);

        regionDao.addOrUpdateRegionById(locationDto);
        provinceDao.addOrUpdateProvinceById(locationDto);
        cityDao.addOrUpdateCityById(locationDto);
        countyDao.addOrUpdateCountyById(locationDto);

        //判断所传的地服是否存在
        LocalStaffEntity localStaffEntity1 = localStaffMapper.findLocalStaffById(object.getString("localStaffId"));

        LocalStaffEntity  localStaffEntity = new LocalStaffEntity();
        localStaffEntity.setLocalStaffId(object.getString("localStaffId"));
        localStaffEntity.setLocalStaffJobNumber(object.getString("localStaffJobNumber"));
        localStaffEntity.setLocalStaffName(object.getString("localStaffName"));
        localStaffEntity.setLocalStaffRegionId(locationDto.getRegionId());
        localStaffEntity.setLocalStaffRegionName(locationDto.getRegionName());
        localStaffEntity.setLocalStaffProvinceId(locationDto.getProvinceId());
        localStaffEntity.setLocalStaffProvinceName(locationDto.getProvinceName());
        localStaffEntity.setLocalStaffCityId(locationDto.getCityId());
        localStaffEntity.setLocalStaffCityName(locationDto.getCityName());
        localStaffEntity.setLocalStaffCountyId(locationDto.getCountyId());
        localStaffEntity.setLocalStaffCountyName(locationDto.getCountyName());

        if(localStaffEntity1 == null){
            localStaffMapper.addLocalStaff(localStaffEntity);
        }else{
            localStaffMapper.updateLocalStaff(localStaffEntity);
        }

    }
}
