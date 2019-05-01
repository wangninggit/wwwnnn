package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.TestingItemSnapshootRecordDto;
import com.viathink.core.business.gene.bean.TestingItemDetailBaseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestingItemDatelyDetailBaseMapper {
    void addTestingItemDetail(TestingItemDetailBaseEntity entity);
    TestingItemDetailBaseEntity findTestingItemDetailByOrderIdAndTestingItemIdAndDatelyStr(TestingItemDetailBaseEntity entity);
    void updateTestingItemDetailByOrderIdAndTestingItemIdAndDatelyStr(TestingItemDetailBaseEntity entity);
    List<TestingItemSnapshootRecordDto> getSnapshootRecordByDatelyStrAndGroupByTestingItemId(String datelyStr);
}
