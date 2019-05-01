package com.viathink.core.business.gene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.EventBaseEntity;
import com.viathink.core.business.gene.bean.RegionEntity;
import com.viathink.core.business.gene.mapper.RegionMapper;
import com.viathink.core.business.gene.service.RegionEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionEventServiceImpl implements RegionEventService {

    private final RegionMapper regionMapper;

    @Autowired
    public RegionEventServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public void updateRegionInfo(EventBaseEntity event, JSONObject object) {

        RegionEntity eventEntity = object.toJavaObject(RegionEntity.class);
        RegionEntity dbEntity = regionMapper.findRegionById(eventEntity.getRegionId());
        if(dbEntity == null){
            regionMapper.addRegion(eventEntity);
        }else{
            dbEntity.setRegionName(eventEntity.getRegionName());
            regionMapper.updateRegionById(dbEntity);
        }
    }
}
