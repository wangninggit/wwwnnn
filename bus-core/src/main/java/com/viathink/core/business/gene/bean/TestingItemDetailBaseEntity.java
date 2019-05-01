package com.viathink.core.business.gene.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestingItemDetailBaseEntity extends EventBaseEntity{
    private Long testingItemOrderIncome = 0L;
    private Long testingItemCashIncome = 0L;
    private Long testingItemFinanceConfirmIncome = 0L;
    private Long testingItemRecordCost = 0L;
    private Long testingItemConfirmRecordCost = 0L;
    private String orderId;
    private String testingResult;
    private String testingAgency;
    private String testingItem;
    private Integer testingItemId = 0;
    private Long testingItemPrice = 0L;
    private Long testingItemCost = 0L;
    private Integer testingAgencyId = 0;
    private String testingAgencyAddress;
    private String testingReportNumber;
    private Long orderCreateTime = 0L;
    private Long orderPayTime = 0L;
    private Long orderInvoiceTime = 0L;
    private Long orderCancelTime = 0L;
    private Long orderUpdateTime = 0L;
    private Long testingReportUploadTime = 0L;
    private Long samplingTime = 0L;
    private Long sampleConfirmTime = 0L;
    private Long testingItemPlaceCount = 0L;
    private Long testingItemCancelCount =0L;
    private String testingReportUrl;

    public TestingItemDetailBaseEntity() {
    }

    public TestingItemDetailBaseEntity(TestingItemEntity testingItemEntity,String orderId) {
        this.setOrderId(orderId);
        this.setTestingResult(testingItemEntity.getTestingResult());
        this.setTestingAgency(testingItemEntity.getTestingAgency());
        this.setTestingItem(testingItemEntity.getTestingItem());
        this.setTestingItemId(testingItemEntity.getTestingItemId());
        this.setTestingItemPrice(testingItemEntity.getTestingItemPrice());
        this.setTestingItemCost(testingItemEntity.getTestingItemCost());
        this.setTestingAgencyId(testingItemEntity.getTestingAgencyId());
        this.setTestingAgencyAddress(testingItemEntity.getTestingAgencyAddress());
        this.setTestingReportNumber(testingItemEntity.getTestingReportNumber());
        this.setTestingReportUploadTime(testingItemEntity.getTestingReportUploadTime());
        this.setSamplingTime(testingItemEntity.getSamplingTime());
        this.setSampleConfirmTime(testingItemEntity.getSampleConfirmTime());
        this.setTestingReportUrl(testingItemEntity.getTestingReportUrl());
    }
}
