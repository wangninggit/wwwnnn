package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.dao.TestingItemDao;
import com.viathink.core.business.gene.mapper.TestingItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestingItemDaoImpl implements TestingItemDao {
    private final TestingItemMapper testingItemMapper;

    @Autowired
    public TestingItemDaoImpl(TestingItemMapper testingItemMapper) {
        this.testingItemMapper = testingItemMapper;
    }

    @Override
    public void updateTestingItem(List<TestingItemEntity> testingItemEntities, String orderId) {
        List<TestingItemEntity > list = testingItemMapper.findTestingItemByOrderId(orderId);
        if(list.size()>0){
            testingItemMapper.deleteTestingItemByOrderId(orderId);
        }
        for(TestingItemEntity testingItemEntity:testingItemEntities){
            testingItemEntity.setOrderId(orderId);
            testingItemMapper.addTestingItem(testingItemEntity);
        }
    }
}
