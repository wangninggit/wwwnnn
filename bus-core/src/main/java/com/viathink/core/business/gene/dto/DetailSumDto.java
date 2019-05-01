package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class DetailSumDto {
    private Long orderIncome = 0L;
    private Long cashIncome = 0L;
    private Long financeConfirmIncome = 0L;
    private Long testingItemCost = 0L;
    private Long testingItemConfirmCost = 0L;
    private Long integralCost = 0L;
    private Long orderPlaceCount = 0L;
    private Long orderPlaceAvgCost = 0L;
    private Long orderCancelCount = 0L;
    private Long orderCancelAvgCost = 0L;
}
