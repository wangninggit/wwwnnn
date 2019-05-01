package com.viathink.api.dict.controller;

import com.viathink.core.business.gene.bean.ProvinceEntity;
import com.viathink.core.business.gene.bean.RegionEntity;
import com.viathink.core.business.gene.bean.TestingItemTabEntity;
import com.viathink.core.business.gene.mapper.ProvinceMapper;
import com.viathink.core.business.gene.mapper.RegionMapper;
import com.viathink.core.business.gene.mapper.TestingItemTabMapper;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DictController {
    private final RegionMapper regionMapper;
    private final ProvinceMapper provinceMapper;
    private final TestingItemTabMapper testingItemTabMapper;

    @Autowired
    public DictController(RegionMapper regionMapper, ProvinceMapper provinceMapper, TestingItemTabMapper testingItemTabMapper) {
        this.regionMapper = regionMapper;
        this.provinceMapper = provinceMapper;
        this.testingItemTabMapper = testingItemTabMapper;
    }

    @RequiresUser
    @GetMapping(value = "/dict/regions")
    public Map getRegionList() {
        Map<String, List<RegionEntity>> map = new HashMap<>();
        List<RegionEntity> list = regionMapper.getRegionList();
        map.put("list", list);
        return map;
    }

    @RequiresUser
    @GetMapping(value = "/dict/provinces")
    public Map getProvinceList() {
        Map<String, List<ProvinceEntity>> map = new HashMap<>();
        List<ProvinceEntity> list = provinceMapper.getProvinceList();
        map.put("list", list);
        return map;
    }

    @RequiresUser
    @GetMapping(value = "/dict/testing-items")
    public Map getTestingItemList() {
        Map<String, List<TestingItemTabEntity>> map = new HashMap<>();
        List<TestingItemTabEntity> list = testingItemTabMapper.findTestingItemList();
        map.put("list", list);
        return map;
    }
}
