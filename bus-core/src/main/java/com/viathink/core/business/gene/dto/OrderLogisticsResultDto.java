package com.viathink.core.business.gene.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderLogisticsResultDto {
    private String express;
    private String trackingNo;
    private String state;
    private Integer code;
    private Boolean result;
    private List data;
}
