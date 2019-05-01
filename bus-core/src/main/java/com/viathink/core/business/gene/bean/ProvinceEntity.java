package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class ProvinceEntity {
    private static final long serialVersionUID = 1L;
    private String provinceId;
    private String provinceName;
    private String regionId;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
