package com.viathink.core.business.gene.dao.Impl;

import com.viathink.core.business.gene.bean.TestingItemEntity;
import com.viathink.core.business.gene.bean.TestingItemTabEntity;
import com.viathink.core.business.gene.dao.TestingItemTabDao;
import com.viathink.core.business.gene.mapper.TestingItemTabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestingItemTabDaoImpl implements TestingItemTabDao {
    private final TestingItemTabMapper testingItemTabMapper;

    @Autowired
    public TestingItemTabDaoImpl(TestingItemTabMapper testingItemTabMapper) {
        this.testingItemTabMapper = testingItemTabMapper;
    }

    @Override
    public void addOrUpdateTestingItem(List<TestingItemEntity> list) {
        for(TestingItemEntity entity : list){
            TestingItemTabEntity testingItemById = testingItemTabMapper.findTestingItemById(entity.getTestingItemId());
            if(testingItemById == null){
                testingItemTabMapper.addTestingItem(new TestingItemTabEntity(entity));
            }else{
                testingItemTabMapper.updateTestingItemById(new TestingItemTabEntity(entity));
            }
        }
    }
}
