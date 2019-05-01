package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.TestingItemTabEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestingItemTabMapper {
    void addTestingItem(TestingItemTabEntity testingItemTabEntity);
    void updateTestingItemById(TestingItemTabEntity testingItemTabEntity);
    TestingItemTabEntity findTestingItemById(Integer id);
    List<TestingItemTabEntity> findTestingItemList();
}
