package com.viathink.core.business.gene.bean;

import lombok.Data;

@Data
public class OrderInvoiceEntity {
    private static final long serialVersionUID = 1L;
    private Long id = 0L;
    private String orderId;
    private String invoiceCode;
    private Boolean deleted;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
