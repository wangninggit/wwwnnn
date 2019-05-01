package com.viathink.core.batch.dto;

import lombok.Data;

@Data
public class TestingItemSnapshootRecordDto {
    private String testingItemId;
    private String testingItem;
    private Long testingItemOrderIncome = 0L;
    private Long testingItemCashIncome = 0L;
    private Long testingItemFinanceConfirmIncome = 0L;
    private Long testingItemRecordCost = 0L;
    private Long testingItemConfirmRecordCost = 0L;
    private Long testingItemPlaceCount = 0L;
    private Long testingItemCancelCount = 0L;
}
