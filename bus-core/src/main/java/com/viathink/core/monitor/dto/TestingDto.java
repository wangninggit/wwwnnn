package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TestingDto {
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Long testingItemId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String testingAgency;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String testingItem;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Long testingItemPrice;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
//    @NotNull(message = "不能为null", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private Long testingItemCost = 0L;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Long testingAgencyId;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String testingAgencyAddress;
//    @Min(value = 1514736000000L, message = "最小值为 1514736000000L（2018年01月01日 00:00:00）", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class})
//    @NotNull(message = "不能为null", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class})
    private Long samplingTime;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class
    })
    private String testingReportNumber;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class
//    })
    private String testingResult;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class
    })
    private String testingReportUrl;
}
