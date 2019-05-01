package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.dto.LocalStaffLocationDto;

public interface RegionDao {
    void addOrUpdateRegionById(LocalStaffLocationDto locationDto);
}
