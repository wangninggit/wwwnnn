package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.TestingItemSnapshootRecordDto;
import com.viathink.core.business.gene.bean.TestingItemDetailBaseEntity;
import com.viathink.core.business.gene.dto.QueryTestingItemContrastParamDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestingItemDailyDetailMapper extends TestingItemDatelyDetailBaseMapper{
    List<TestingItemSnapshootRecordDto> getSnapshootRecordByDatelyRangeAndTestingItemIdsAndGroupByTestingItemId(QueryTestingItemContrastParamDto queryTestingItemContrastParamDto);
}
