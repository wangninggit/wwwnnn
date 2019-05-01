package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class DashboardOrderTrendDto {
    private Long orderPlaceCount = 0L;
    private Long orderPlaceCost = 0L;
    private String date;
    private Long orderCreateTime = 0L;
}
