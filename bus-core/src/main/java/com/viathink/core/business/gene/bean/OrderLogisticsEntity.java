package com.viathink.core.business.gene.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderLogisticsEntity {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tag;
    private String messageId;
    private Long recordId;
    private String event;
    private Long eventTime;
    private String orderId;
    private String logisticsType;
    @NotNull(message="{order.logistics.expressNumber}")
    private String expressNumber;
    private String expressCompanyName;
    @NotNull(message="{order.logistics.expressCompanyId}")
    private String expressCompanyId;
    private Long updateTime;
    private Long createTime;
}
