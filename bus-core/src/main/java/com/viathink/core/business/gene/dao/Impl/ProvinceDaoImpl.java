package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.ProvinceEntity;
import com.viathink.core.business.gene.dao.ProvinceDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvinceDaoImpl implements ProvinceDao {
    private final ProvinceMapper provinceMapper;

    @Autowired
    public ProvinceDaoImpl(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    @Override
    public void addOrUpdateProvinceById(LocalStaffLocationDto locationDto) {
        ProvinceEntity provinceEntity = provinceMapper.findProvinceById(locationDto.getProvinceId());
        if (provinceEntity == null) {
            provinceEntity = new ProvinceEntity();
            provinceEntity.setRegionId(locationDto.getRegionId());
            provinceEntity.setProvinceId(locationDto.getProvinceId());
            provinceEntity.setProvinceName(locationDto.getProvinceName());
            provinceMapper.addProvince(provinceEntity);
        } else {
            provinceEntity.setRegionId(locationDto.getRegionId());
            provinceEntity.setProvinceId(locationDto.getProvinceId());
            provinceEntity.setProvinceName(locationDto.getProvinceName());
            provinceMapper.updateProvince(provinceEntity);
        }
    }
}
