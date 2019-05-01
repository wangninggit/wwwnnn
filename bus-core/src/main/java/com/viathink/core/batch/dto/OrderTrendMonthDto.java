package com.viathink.core.batch.dto;

import lombok.Data;

@Data
public class OrderTrendMonthDto {
    private Long orderPlaceAvgCost = 0L;
    private Long orderPlaceAvgCount = 0L;
}
