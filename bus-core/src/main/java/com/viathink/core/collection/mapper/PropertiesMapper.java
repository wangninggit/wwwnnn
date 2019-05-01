package com.viathink.core.collection.mapper;

import com.viathink.core.collection.bean.PropertiesEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesMapper {
    void addProperty(PropertiesEntity tokenEntity);
    PropertiesEntity findPropertyById(String id);
    void updateProperty(PropertiesEntity tokenEntity);
}
