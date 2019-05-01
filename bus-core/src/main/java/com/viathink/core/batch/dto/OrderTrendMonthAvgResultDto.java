package com.viathink.core.batch.dto;

import lombok.Data;

@Data
public class OrderTrendMonthAvgResultDto {
    private Double holidayPlaceAvgCost = 0.00;
    private Long holidayPlaceAvgCount = 0L;
    private Double workPlaceAvgCost = 0.00;
    private Long workPlaceAvgCount = 0L;
    private String date;
}
