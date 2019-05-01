package com.viathink.core.business.gene.dto;

import com.viathink.core.util.SortServiceUtil;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class QueryTestingItemContrastParamDto {
    private String[] testingItemIds;
    @NotNull(message = "{export.DetailCountForm.startDate}")
    @Min(value = 1000000000000L, message = "{export.DetailCountForm.startDate}")
    private Long startDate;
    @NotNull(message = "{export.DetailCountForm.endDate}")
    @Min(value = 1000000000000L, message = "{export.DetailCountForm.endDate}")
    private Long endDate;
    private String sortBy = SortServiceUtil.SORT_BY[0];
    private String sortMode = SortServiceUtil.SORT_MODE[0];
}
