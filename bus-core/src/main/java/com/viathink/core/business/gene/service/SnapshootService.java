package com.viathink.core.business.gene.service;

import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.dto.DateRangeStrDto;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface SnapshootService {
    SnapshootEntity findSnapshootByClass(SnapshootEntity entity);
    List<OrderTrendMonthResultDto> findSnapshootByMonthAvg(DateRangeStrDto dateRangeStrDto, PageParam pageParam,Boolean isPage);
    Long getIntegralRatio();
}
