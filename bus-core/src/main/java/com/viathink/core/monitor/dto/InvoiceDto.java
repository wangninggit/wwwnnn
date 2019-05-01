package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InvoiceDto {
    public interface group{}

    @NotEmpty(message = "数组不能为空，且大小不能为0", groups = {InvoiceDto.group.class})
    private List<String> orderIds;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceDate;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceGoodsCodeVersion;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceTaxClassificationCode;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceCode;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceNumber;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceBuyerName;
    //@NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceBuyerTaxPayerNumber;
    // @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceBuyerAddress;
    // @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceBuyerBank;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceItemName;
    // @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    // @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoiceItemCount;
    // @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    // @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoiceItemUnitPrice;
    @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoicePostTaxAmount;
    @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoiceTaxRate;
    @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoiceTaxAmount;
    @Min(value = 0, message = "最小值为0", groups = {InvoiceDto.group.class})
    @NotNull(message = "不能为null", groups = {InvoiceDto.group.class})
    private Long invoiceAmount;
    @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceSellerName;
    // @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceSellerTaxPayerNumber;
    // @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceSellerAddress;
    // @NotBlank(message = "不能为空字符串", groups = {InvoiceDto.group.class})
    private String invoiceSellerBank;
}
