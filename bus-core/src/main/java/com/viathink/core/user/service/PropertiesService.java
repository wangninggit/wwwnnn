package com.viathink.core.user.service;

import com.viathink.core.collection.bean.PropertiesEntity;

public interface PropertiesService {
    void updateProperties(PropertiesEntity propertiesEntity);
    PropertiesEntity getPropertiesById(String id);
}
