package com.viathink.core.business.gene.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class GeneDetailBaseEntity extends EventBaseEntity {

    private Long orderIncome = 0L;
    private Long cashIncome = 0L;
    private Long financeConfirmIncome = 0L;
    private Long testingItemCost = 0L;
    private Long testingItemConfirmCost = 0L;
    private Long integralCost = 0L;
    private Long dcwIntegralCost = 0L;
    private Long dcwIntegralRawCost = 0L;
    private Long empiriValue = 0L;
    private Integer changeCount = 0;
    private String orderId;
    private String orderNumber;
    private String orderCreatorId;
    private String orderCreatorName;
    private Long orderPrice = 0L;
    private Long orderCreateTime = 0L;
    private Long orderPayTime = 0L;
    private Long sampleConfirmTime = 0L;
    private Long testingReportUploadTime = 0L;
    private Long integralGrantTime = 0L;
    private Long orderInvoiceTime = 0L;
    private Long orderCancelTime = 0L;
    private Long orderRefundTime = 0L;
    private Long orderUpdateTime = 0L;
    private String patientName;
    private Integer patientAge;
    private String patientClinicalDiagnosis;
    private String patientIdCard;
    private String patientAddress;
    private String patientGender;
    private String localStaffId;
    private String localStaffJobNumber;
    private String localStaffName;
    private String localStaffRegionId;
    private String localStaffRegionName;
    private String localStaffProvinceId;
    private String localStaffProvinceName;
    private String localStaffCityId;
    private String localStaffCityName;
    private String localStaffCountyId;
    private String localStaffCountyName;
    private String hospitalName;
    private String hospitalAddress;
    private String doctorId;
    private String doctorName;
    private Long integral = 0L;
    private Long dcwIntegral = 0L;
    private Long dcwIntegralRaw = 0L;
    private String dcwId;
    private Integer testingItemId;
    private List<TestingItemEntity> testings = new ArrayList<>();
    private Long orderPlaceCount = 0L;
    private Long orderPlaceCost = 0L;
    private Long orderCancelCount = 0L;
    private Long orderCancelCost = 0L;
    //支付相关字段
    private String tradeNumber;
    private String orderPayAttorneyUrl;
    private String orderPayerAccount;
    private Long payTotal = 0L;
    private String paymentType;
    private String orderPayerName;
    private Boolean localStaffPayroll = false;
    //退款相关字段
    private Long refundTotal = 0L;
    private String refundType;
    private String refundReceiverAccount;
    private String refundReceiverName;
}
