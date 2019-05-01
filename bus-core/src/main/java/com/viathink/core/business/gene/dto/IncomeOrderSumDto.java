package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class IncomeOrderSumDto {
    private Long orderPrice = 0L;
//    private Long invoicePostTaxAmount = 0L;
//    private Long invoiceTaxAmount = 0L;
//    private Long invoiceAmount = 0L;
    private Long testingItemCost = 0L;
    private Long testingItemPrice = 0L;
}
