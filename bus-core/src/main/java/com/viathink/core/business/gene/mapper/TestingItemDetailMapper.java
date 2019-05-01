package com.viathink.core.business.gene.mapper;

import com.viathink.core.business.gene.bean.TestingItemDetailBaseEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestingItemDetailMapper {
    void addTestingItemDetail(TestingItemDetailBaseEntity entity);
    List<TestingItemDetailBaseEntity> getTestingItemDetailsByOrderId(String orderId);
    void updateTestingItemDetailByOrderIdAndTestingItemId(TestingItemDetailBaseEntity entity);
}
