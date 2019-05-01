package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.bean.TestingItemEntity;

import java.util.List;

public interface TestingItemTabDao {
    void addOrUpdateTestingItem(List<TestingItemEntity> list);
}
