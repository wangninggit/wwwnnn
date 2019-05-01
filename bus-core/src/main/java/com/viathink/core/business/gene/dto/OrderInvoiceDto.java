package com.viathink.core.business.gene.dto;

import lombok.Data;

@Data
public class OrderInvoiceDto {
    private String invoiceCode;
    private String[] orderIds;

}
