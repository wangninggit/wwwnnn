package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.CountyEntity;
import com.viathink.core.business.gene.dao.CountyDao;
import com.viathink.core.business.gene.dto.LocalStaffLocationDto;
import com.viathink.core.business.gene.mapper.CountyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountyDaoImpl implements CountyDao {
    private final CountyMapper countyMapper;

    @Autowired
    public CountyDaoImpl(CountyMapper countyMapper) {
        this.countyMapper = countyMapper;
    }

    @Override
    public void addOrUpdateCountyById(LocalStaffLocationDto locationDto) {
        if (locationDto != null) {
            if (locationDto.getCountyId() != null && !locationDto.getCountyId().equals("")) {
                CountyEntity countyEntity = countyMapper.findCountyById(locationDto.getCountyId());
                if (countyEntity ==  null) {
                    countyEntity = new CountyEntity();
                    countyEntity.setCountyId(locationDto.getCountyId());
                    countyEntity.setCountyName(locationDto.getCountyName());
                    countyMapper.addCounty(countyEntity);
                } else {
                    countyEntity.setCountyId(locationDto.getCountyId());
                    countyEntity.setCountyName(locationDto.getCountyName());
                    countyMapper.updateCountyById(countyEntity);
                }
            }
        }
    }
}
