package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.CityEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.mapper.CityMapper;
import com.viathink.core.business.gene.service.CityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityEventServiceImpl implements CityEventService {

    private final CityMapper cityMapper;

    @Autowired
    public CityEventServiceImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public void updateCityInfo(EventBaseEntity event, JSONObject object) {
        CityEntity eventEntity = object.toJavaObject(CityEntity.class);
        CityEntity dbEntity = cityMapper.findCityById(eventEntity.getCityId());
        if(dbEntity == null){
            cityMapper.addCity(eventEntity);
        }else{
            dbEntity.setCityName(eventEntity.getCityName());
            cityMapper.updateCityById(dbEntity);
        }
    }
}
