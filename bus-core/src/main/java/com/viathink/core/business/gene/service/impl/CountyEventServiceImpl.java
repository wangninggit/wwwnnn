package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.CountyEntity;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.mapper.CountyMapper;
import com.viathink.core.business.gene.service.CountyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountyEventServiceImpl implements CountyEventService {

    private final CountyMapper countyMapper;

    @Autowired
    public CountyEventServiceImpl(CountyMapper countyMapper) {
        this.countyMapper = countyMapper;
    }

    @Override
    public void updateCountyInfo(EventBaseEntity event, JSONObject object) {

        CountyEntity eventEntity = object.toJavaObject(CountyEntity.class);
        CountyEntity dbEntity = countyMapper.findCountyById(eventEntity.getCountyId());
        if(dbEntity == null){
            countyMapper.addCounty(eventEntity);
        }else{
            dbEntity.setCountyName(eventEntity.getCountyName());
            countyMapper.updateCountyById(dbEntity);
        }
    }
}
