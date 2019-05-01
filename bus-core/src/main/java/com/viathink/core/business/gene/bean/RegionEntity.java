package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class RegionEntity {
    private static final long serialVersionUID = 1L;
    private String regionId;
    private String regionName;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
