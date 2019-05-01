package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.dto.LocalStaffLocationDto;

public interface LocalStaffDao {
    void addOrUpdateLocalStaffById(String localStaffId,String localStaffJobNumber,String localStaffName, LocalStaffLocationDto locationDto);
}
