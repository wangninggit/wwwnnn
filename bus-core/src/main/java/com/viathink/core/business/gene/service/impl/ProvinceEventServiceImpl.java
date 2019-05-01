package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.ProvinceEntity;
import com.viathink.core.business.gene.mapper.ProvinceMapper;
import com.viathink.core.business.gene.service.ProvinceEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvinceEventServiceImpl implements ProvinceEventService {

    private final ProvinceMapper provinceMapper;

    @Autowired
    public ProvinceEventServiceImpl(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    @Override
    public void updateProvinceInfo(EventBaseEntity event, JSONObject object) {
        ProvinceEntity eventEntity = object.toJavaObject(ProvinceEntity.class);
        ProvinceEntity dbEntity = provinceMapper.findProvinceById(eventEntity.getProvinceId());
        if(dbEntity == null){
            provinceMapper.addProvince(eventEntity);
        }else{
            dbEntity.setProvinceName(eventEntity.getProvinceName());
            provinceMapper.updateProvince(dbEntity);
        }
    }
    @Override
    public void updateProvinceRegionRelation(EventBaseEntity event, JSONObject object) {
        ProvinceEntity eventEntity = object.toJavaObject(ProvinceEntity.class);
        ProvinceEntity dbEntity = provinceMapper.findProvinceById(eventEntity.getProvinceId());
        if(dbEntity != null) {
            dbEntity.setRegionId(eventEntity.getRegionId());
            provinceMapper.updateProvince(dbEntity);
        }
    }
}
