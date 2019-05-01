package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class CityEntity {
    private static final long serialVersionUID = 1L;
    private String cityId;
    private String cityName;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
