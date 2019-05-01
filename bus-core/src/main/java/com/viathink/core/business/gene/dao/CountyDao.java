package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.dto.LocalStaffLocationDto;

public interface CountyDao {
    void addOrUpdateCountyById(LocalStaffLocationDto locationDto);
}
