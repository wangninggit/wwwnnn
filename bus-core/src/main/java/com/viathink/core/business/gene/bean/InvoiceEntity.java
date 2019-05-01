package com.viathink.core.business.gene.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class InvoiceEntity {
    private String invoiceDate;
    private String invoiceGoodsCodeVersion;
    private String invoiceTaxClassificationCode;
    private String invoiceCode;
    private String invoiceNumber;
    private String invoiceBuyerName;
    private String invoiceBuyerTaxPayerNumber;
    private String invoiceBuyerAddress;
    private String invoiceBuyerBank;
    private String invoiceItemName;
    private Long invoiceItemCount = 0L;
    private Long invoiceItemUnitPrice = 0L;
    private Long invoicePostTaxAmount = 0L;
    private Long invoiceTaxRate = 0L;
    private Long invoiceTaxAmount = 0L;
    private Long invoiceAmount = 0L;
    private Long invoiceTime = 0L;
    private String invoiceSellerName;
    private String invoiceSellerTaxPayerNumber;
    private String invoiceSellerAddress;
    private String invoiceSellerBank;
    private Boolean deleted;
    private List<String> orderIds;
    private Long updateTime = 0L;
    private Long createTime = 0L;
}
