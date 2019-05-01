package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class TestingItemTabEntity {
    private Integer testingItemId;
    private String testingItem;
    private String testingAgency;
    private Integer testingAgencyId;
    private String testingAgencyAddress;

    public TestingItemTabEntity() {
    }

    public TestingItemTabEntity(TestingItemEntity testingItemEntity) {
        this.testingItemId = testingItemEntity.getTestingItemId();
        this.testingItem = testingItemEntity.getTestingItem();
        this.testingAgency = testingItemEntity.getTestingAgency();
        this.testingAgencyId = testingItemEntity.getTestingAgencyId();
        this.testingAgencyAddress = testingItemEntity.getTestingAgencyAddress();
    }
}
