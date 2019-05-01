package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LogisticsDto {
    public interface OrderGroup{}
    public interface RgwlGroup{}

    @NotBlank(message = "不能为空字符串", groups = {LogisticsDto.OrderGroup.class, LogisticsDto.RgwlGroup.class})
    private String orderId;
    @NotBlank(message = "不能为空字符串", groups = {LogisticsDto.OrderGroup.class, LogisticsDto.RgwlGroup.class})
    private String logisticsType;
    @NotNull(message = "不能为null", groups = {LogisticsDto.OrderGroup.class, LogisticsDto.RgwlGroup.class})
    @Valid
    private LogisticsDetailDto detail;
}

@Data
class LogisticsDetailDto {
    @NotBlank(message = "不能为空字符串", groups = {LogisticsDto.OrderGroup.class})
    private String expressNumber;
    @NotBlank(message = "不能为空字符串", groups = {LogisticsDto.OrderGroup.class, LogisticsDto.RgwlGroup.class})
    private String expressCompanyName;
    @NotBlank(message = "不能为空字符串", groups = {LogisticsDto.OrderGroup.class, LogisticsDto.RgwlGroup.class})
    private String expressCompanyId;
}
