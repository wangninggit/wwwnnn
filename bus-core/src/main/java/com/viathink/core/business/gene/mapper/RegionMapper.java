package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.RegionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionMapper {
    void addRegion(RegionEntity entity);
    void updateRegionById(RegionEntity entity);
    RegionEntity findRegionById(String cityId);
    List<RegionEntity> getRegionList();
}
