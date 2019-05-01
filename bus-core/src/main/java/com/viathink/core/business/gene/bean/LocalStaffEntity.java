package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class LocalStaffEntity {
    private static final long serialVersionUID = 1L;
    private String localStaffId;
    private String localStaffJobNumber;
    private String localStaffName;
    private String localStaffRegionId;
    private String localStaffRegionName;
    private String localStaffProvinceId;
    private String localStaffProvinceName;
    private String localStaffCityId;
    private String localStaffCityName;
    private String localStaffCountyId;
    private String localStaffCountyName;
    private Long updateTime;
    private Long createTime;
}
