package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class CountyEntity {
    private static final long serialVersionUID = 1L;
    private String countyId;
    private String countyName;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
