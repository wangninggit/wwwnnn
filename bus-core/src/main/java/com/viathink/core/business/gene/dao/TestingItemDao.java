package com.viathink.core.business.gene.dao;

import com.viathink.core.business.gene.bean.TestingItemEntity;

import java.util.List;

public interface TestingItemDao {
    void updateTestingItem(List<TestingItemEntity> testingItemEntities,String orderId);
}
