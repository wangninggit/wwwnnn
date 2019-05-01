package com.viathink.core.business.gene.service;

import com.viathink.core.business.gene.dto.DateRangeDto;
import com.viathink.core.business.gene.dto.IncomeOrderDto;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface IncomeReportService {
    List<IncomeOrderDto> getGeneDetailIncomeReportByPage(DateRangeDto dateRangeDto, PageParam pageParam,Boolean isPage);
}
