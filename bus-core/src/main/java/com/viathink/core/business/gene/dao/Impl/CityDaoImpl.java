package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.CityEntity;
import com.viathink.core.business.gene.dao.CityDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityDaoImpl implements CityDao {
    private final CityMapper cityMapper;

    @Autowired
    public CityDaoImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public void addOrUpdateCityById(LocalStaffLocationDto locationDto) {
        CityEntity cityEntity = cityMapper.findCityById(locationDto.getCityId());
        if (cityEntity == null) {
            cityEntity = new CityEntity();
            cityEntity.setCityId(locationDto.getCityId());
            cityEntity.setCityName(locationDto.getCityName());
            cityMapper.addCity(cityEntity);
        } else {
            cityEntity.setCityId(locationDto.getCityId());
            cityEntity.setCityName(locationDto.getCityName());
            cityMapper.updateCityById(cityEntity);
        }
    }
}
