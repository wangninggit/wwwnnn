package com.viathink.core.user.service.impl;

import com.viathink.core.collection.bean.PropertiesEntity;
import com.viathink.core.collection.mapper.PropertiesMapper;
import com.viathink.core.user.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Override
    public void updateProperties(PropertiesEntity propertiesEntity) {

        propertiesMapper.updateProperty(propertiesEntity);
    }

    @Override
    public PropertiesEntity getPropertiesById(String id) {
        return propertiesMapper.findPropertyById(id);
    }
}
