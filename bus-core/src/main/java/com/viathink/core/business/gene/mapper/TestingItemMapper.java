package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.TestingItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestingItemMapper {
    void addTestingItem(TestingItemEntity testingItemEntity);
    void updateTestingItemById(TestingItemEntity testingItemEntity);
    List<TestingItemEntity> findTestingItemByOrderId(String orderId);
    TestingItemEntity findTestingItemById(Long testingItemId);
    void deleteTestingItemByOrderId(String orderId);
}
