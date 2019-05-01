package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.RegionEntity;
import com.viathink.core.business.gene.dao.RegionDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionDaoImpl implements RegionDao {
    private final RegionMapper regionMapper;

    @Autowired
    public RegionDaoImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public void addOrUpdateRegionById(LocalStaffLocationDto locationDto) {
        RegionEntity regionEntity = regionMapper.findRegionById(locationDto.getRegionId());
        if (regionEntity == null) {
            regionEntity = new RegionEntity();
            regionEntity.setRegionName(locationDto.getRegionName());
            regionEntity.setRegionId(locationDto.getRegionId());
            regionMapper.addRegion(regionEntity);
        } else {
            regionEntity.setRegionName(locationDto.getRegionName());
            regionEntity.setRegionId(locationDto.getRegionId());
            regionMapper.updateRegionById(regionEntity);
        }
    }
}
