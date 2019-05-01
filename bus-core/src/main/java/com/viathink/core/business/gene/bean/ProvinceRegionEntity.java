package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class ProvinceRegionEntity {
    private static final long serialVersionUID = 1L;
    private Long id = 0L;
    private String provinceId;
    private String regionId;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
