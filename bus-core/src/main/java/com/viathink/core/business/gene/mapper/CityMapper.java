package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.CityEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CityMapper {
    void addCity(CityEntity entity);
    void updateCityById(CityEntity entity);
    CityEntity findCityById(String cityId);
}
