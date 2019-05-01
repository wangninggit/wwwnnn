package com.viathink.api.user.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.collection.bean.PropertiesEntity;
import com.viathink.core.user.service.PropertiesService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PropertiesController {
    private final PropertiesService propertiesService;

    @Autowired
    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @RequiresRoles("admin")
    @PutMapping(value = "/properties/{id}")
    public ResponseEntity updateProperties(@PathVariable String id, @Valid @RequestBody PropertiesEntity propertiesEntity){
        propertiesEntity.setId(id);
        propertiesService.updateProperties(propertiesEntity);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequiresRoles("admin")
    @GetMapping(value = "/properties/{id}")
    public PropertiesEntity getProperties(@PathVariable String id) {
        PropertiesEntity propertiesEntity = propertiesService.getPropertiesById(id);
        if (propertiesEntity == null) {
            throw new ParamsInvalidException(4003,"积分系数不存在");
        }
        return propertiesEntity;
    }
}
