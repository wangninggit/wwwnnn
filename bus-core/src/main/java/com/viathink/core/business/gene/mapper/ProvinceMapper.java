package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.ProvinceEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceMapper {
    void addProvince(ProvinceEntity provinceEntity);
    void updateProvince(ProvinceEntity provinceEntity);
    ProvinceEntity findProvinceById(String id);
    List<ProvinceEntity> getProvinceList();
}
