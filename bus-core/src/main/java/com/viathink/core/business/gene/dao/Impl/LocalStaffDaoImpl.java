package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.LocalStaffEntity;
import com.viathink.core.business.gene.dao.LocalStaffDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.LocalStaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalStaffDaoImpl implements LocalStaffDao {
    private final LocalStaffMapper localStaffMapper;

    @Autowired
    public LocalStaffDaoImpl(LocalStaffMapper localStaffMapper) {
        this.localStaffMapper = localStaffMapper;
    }

    @Override
    public void addOrUpdateLocalStaffById(String localStaffId,String localStaffJobNumber,String localStaffName, LocalStaffLocationDto locationDto) {
        LocalStaffEntity localStaffEntity = localStaffMapper.findLocalStaffById(localStaffId);
        LocalStaffEntity entity;
        if (localStaffEntity == null)
            entity = new LocalStaffEntity();
        else
            entity = localStaffEntity;

        entity.setLocalStaffId(localStaffId);
        entity.setLocalStaffJobNumber(localStaffJobNumber);
        entity.setLocalStaffName(localStaffName);
        entity.setLocalStaffRegionId(locationDto.getRegionId());
        entity.setLocalStaffRegionName(locationDto.getRegionName());
        entity.setLocalStaffProvinceId(locationDto.getProvinceId());
        entity.setLocalStaffProvinceName(locationDto.getProvinceName());
        entity.setLocalStaffCityId(locationDto.getCityId());
        entity.setLocalStaffCityName(locationDto.getCityName());
        entity.setLocalStaffCountyId(locationDto.getCountyId());
        entity.setLocalStaffCountyName(locationDto.getCountyName());
        if (localStaffEntity == null)
            localStaffMapper.addLocalStaff(entity);
        else
            localStaffMapper.updateLocalStaff(entity);
    }
}
