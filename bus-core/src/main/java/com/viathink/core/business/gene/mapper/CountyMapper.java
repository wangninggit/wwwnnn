package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.CountyEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyMapper {
    void addCounty(CountyEntity entity);
    void updateCountyById(CountyEntity entity);
    CountyEntity findCountyById(String cityId);
}
