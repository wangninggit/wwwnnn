package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class DashboardOrderTrendParamDto {
    private Long orderPlaceCount = 0L;
    private Double orderPlaceCost = 0.00;
    private String date;
}
