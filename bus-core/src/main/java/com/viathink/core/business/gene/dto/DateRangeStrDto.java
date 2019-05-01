package com.viathink.core.business.gene.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DateRangeStrDto {
    @NotNull(message="{export.DateRangeStrDto.startDateStr}")
    private String startDateStr;
    @NotNull(message="{export.DateRangeStrDto.endDateStr}")
    private String endDateStr;
    private String date;
    @NotNull(message="{export.DateRangeStrDto.timeRange}")
    private String timeRange;
}
