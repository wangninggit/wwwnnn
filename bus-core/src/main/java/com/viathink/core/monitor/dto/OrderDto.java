package com.viathink.core.monitor.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    public interface orderCreateGroup { }
    public interface orderPayedGroup { }
    public interface sampleConfirmed{ }
    public interface uploadTestingReportGroup { }
    public interface integralGrantGroup { }
    public interface orderRefundedGroup{ }

    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String orderId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String orderNumber;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String orderCreatorId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String orderCreatorName;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Long orderPrice;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String patientName;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String patientAddress = null;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String patientGender;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String patientIdCard;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @Max(value = 150, message = "最大值为150", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Integer patientAge;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String patientClinicalDiagnosis;
    @NotNull(message = "不能为null", groups = {
            OrderDto.sampleConfirmed.class, OrderDto.uploadTestingReportGroup.class
    })
    private Long testingItemId;
//    @NotNull(message = "不能为null", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    @Valid
    List<TestingDto> testings = new ArrayList<>();
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String localStaffId;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
//            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
//    })
    private String localStaffJobNumber;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String localStaffName;
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @Valid
    private LocalStaffLocationFieldDto localStaffLocation;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String hospitalName;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String hospitalAddress;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String doctorId;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private String doctorName;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderCreateGroup.class, OrderDto.orderPayedGroup.class, OrderDto.sampleConfirmed.class,
            OrderDto.uploadTestingReportGroup.class, OrderDto.integralGrantGroup.class, OrderDto.orderRefundedGroup.class
    })
    private Long integral;
    @NotBlank(message = "不能为空字符串", groups = {OrderDto.integralGrantGroup.class})
    private String dcwId;

    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderPayedGroup.class
    })
    private String paymentType;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderPayedGroup.class
    })
    private String orderPayerName;
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderPayedGroup.class
    })
    private Boolean localStaffPayroll;
//    @NotBlank(message = "不能为空字符串", groups = {
//            OrderDto.orderPayedGroup.class
//    })
    private String orderPayAttorneyUrl;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderPayedGroup.class
    })
    private String orderPayerAccount;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderPayedGroup.class
    })
    private String tradeNumber;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderPayedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderPayedGroup.class
    })
    private Long payTotal;

    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.orderRefundedGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.orderRefundedGroup.class
    })
    private Long refundTotal = 0L;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderRefundedGroup.class
    })
    private String refundType;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderRefundedGroup.class
    })
    private String refundReceiverAccount;
    @NotBlank(message = "不能为空字符串", groups = {
            OrderDto.orderRefundedGroup.class
    })
    private String refundReceiverName;
//    @Min(value = 0, message = "最小值为0", groups = {
//            OrderDto.integralGrantGroup.class
//    })
//    @NotNull(message = "不能为null", groups = {
//            OrderDto.integralGrantGroup.class
//    })
    private Long dcwIntegral = 0L;
    @Min(value = 0, message = "最小值为0", groups = {
            OrderDto.integralGrantGroup.class
    })
    @NotNull(message = "不能为null", groups = {
            OrderDto.integralGrantGroup.class
    })
    private Long dcwIntegralRaw = 0L;

}

