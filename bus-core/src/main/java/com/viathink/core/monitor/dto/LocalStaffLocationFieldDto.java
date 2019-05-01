package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocalStaffLocationFieldDto{
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String regionId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String regionName;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String provinceId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String provinceName;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String cityId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class,
            LocalStaffReimburseDto.Group.class, LocalStaffReimburseDto.Group.class
    })
    private String cityName;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String countyId;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String countyName;
}
