package com.viathink.core.monitor.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.business.gene.bean.*;
import com.viathink.core.business.gene.mapper.*;
import com.viathink.core.monitor.dto.*;
import com.viathink.core.monitor.service.MonitorService;
import com.viathink.core.monitor.util.MonitorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageMonitorServiceImpl implements MonitorService {
    private final Validator engineValidator;
    private final ErrorLogMapper errorLogMapper;
    private final GeneDetailMapper geneDetailMapper;
    private final ProvinceMapper provinceMapper;
    private final RegionMapper regionMapper;
    private final LocalStaffMapper localStaffMapper;

    private Logger logger = LoggerFactory.getLogger(MessageMonitorServiceImpl.class);

    @Autowired
    public MessageMonitorServiceImpl(Validator engineValidator, ErrorLogMapper errorLogMapper, GeneDetailMapper geneDetailMapper, ProvinceMapper provinceMapper, RegionMapper regionMapper, LocalStaffMapper localStaffMapper) {
        this.engineValidator = engineValidator;
        this.errorLogMapper = errorLogMapper;
        this.geneDetailMapper = geneDetailMapper;
        this.provinceMapper = provinceMapper;
        this.regionMapper = regionMapper;
        this.localStaffMapper = localStaffMapper;
    }

    @Override
    public Boolean messageCheck(JSONObject jsonObject, String messageId) {
        List<String> errorList;
        ErrorLogEntity resultDto = new ErrorLogEntity();
        ErrorDetailFieldCheckDto fieldCheckDto = new ErrorDetailFieldCheckDto();

        fieldCheckDto.setMessageId(messageId);
        fieldCheckDto.setEvent(jsonObject.getString("event"));
        fieldCheckDto.setEventTime(jsonObject.getLong("eventTime"));
        fieldCheckDto.setRecordId(jsonObject.getLong("id"));
        fieldCheckDto.setTag(jsonObject.getString("tag"));
        resultDto.setError("字段校验错误");
        resultDto.setType(MonitorUtil.ERROR_TYPE_INTERFACE);
        resultDto.setStatus(false);

        errorList = this.validate(jsonObject.toJavaObject(EventDto.class), EventDto.group.class);
        if(errorList.isEmpty()){
            errorList.addAll(fieldCheck(jsonObject));
            errorList.addAll(orderRepeatCheck(jsonObject));
        }

        if (!errorList.isEmpty()) {
            logger.warn("字段检查出错：" + errorList);
            fieldCheckDto.setErrorList(errorList);
            resultDto.setDetail(JSONObject.toJSONString(fieldCheckDto));
            errorLogMapper.addErrorLog(resultDto);
            return false;
        }

        return true;
    }

    // 字段检查部分
    private List<String> fieldCheck(JSONObject jsonObject) {
        List<String> list;

        //校验 eventDto
        EventDto eventDto = jsonObject.toJavaObject(EventDto.class);
        switch (eventDto.getTag()) {
            case "order":
                switch (eventDto.getEvent()) {
                    case "orderCreated":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.orderCreateGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "orderPayed":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.orderPayedGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "sampleConfirmed":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.sampleConfirmed.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "testingReportUploaded":
                        list = geneTestingReportUploadFieldCheck(jsonObject);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "integralGranted":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.integralGrantGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "orderInvoiced":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(InvoiceDto.class), InvoiceDto.group.class);
                        break;
                    case "orderCancel":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.orderCreateGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "orderRefunded":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.orderRefundedGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "orderUpdated":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.orderCreateGroup.class);
                        list.addAll(geneTestingsRepeatCheck(jsonObject));
                        break;
                    case "orderLogistics":
                        list = orderLogisticsFieldCheck(jsonObject);
                        break;
                    default:
                        list = unknownMessage();
                        break;
                }
                break;
            case "logistics":
                switch (eventDto.getEvent()) {
                    case "localStaffReimburseApproved":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(LocalStaffReimburseDto.class), LocalStaffReimburseDto.Group.class);
                        break;
                    default:
                        list = unknownMessage();
                        break;
                }
                break;
            case "system":
                switch (eventDto.getEvent()) {
                    case "localStaffUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(LocalStaffInfoDto.class), LocalStaffInfoDto.Group.class);
                        break;
                    case "doctorUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(DoctorInfoDto.class), DoctorInfoDto.Group.class);
                        break;
                    case "provinceUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(ProvinceInfoDto.class), ProvinceInfoDto.Group.class);
                        break;
                    case "cityUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(CityInfoDto.class), CityInfoDto.Group.class);
                        break;
                    case "countyUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(CountyInfoDto.class), CountyInfoDto.Group.class);
                        break;
                    case "regionUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(RegionInfoDto.class), RegionInfoDto.Group.class);
                        break;
                    case "regionProvinceUpdate":
                        list = this.validate(jsonObject.getJSONObject("data").toJavaObject(RegionProvinceDto.class), RegionProvinceDto.Group.class);
                        break;
                    default:
                        list = unknownMessage();
                        break;
                }
                break;
            default:
                list = unknownMessage();
                break;
        }

        return list;
    }

    private List<String> validate(Object dto, Class group) {
        Set<ConstraintViolation<Object>> constraintViolations = engineValidator.validate(dto, group);
        List<String> fieldErrorList = new ArrayList<>();
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                String filedError = constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage();
                fieldErrorList.add(filedError);
            }
        }
        return fieldErrorList;
    }

    private List<String> unknownMessage() {
        List<String> fieldErrorList = new ArrayList<>();
        fieldErrorList.add("unknown Message");
        return fieldErrorList;
    }

    private List<String> geneTestingReportUploadFieldCheck(JSONObject jsonObject) {
        List<String> list = this.validate(jsonObject.getJSONObject("data").toJavaObject(OrderDto.class), OrderDto.sampleConfirmed.class);
        if(jsonObject.getJSONObject("data").getJSONArray("testings") != null) {

            Long testingItemId = jsonObject.getJSONObject("data").getLong("testingItemId");
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("testings");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if(object != null ){
                    if (testingItemId != null && object.getLong("testingItemId").equals(testingItemId)) {
                        list.addAll(this.validate(object.toJavaObject(TestingDto.class), OrderDto.uploadTestingReportGroup.class));
                    }
                }
            }
        }else{
            list.add("testings 为null");
        }
        return list;
    }

    private List<String> geneTestingsRepeatCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();
        if(jsonObject.getJSONObject("data").getJSONArray("testings") != null) {

            List<Long> longList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("testings");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if(object != null && object.getLong("testingItemId") != null){
                    Long testingItemId = object.getLong("testingItemId");
                    if(longList.contains(testingItemId)){
                        list.add("检测套餐testingItemId重复，testingItemId：" + testingItemId);
                    }else{
                        longList.add(testingItemId);
                    }
                }
            }
        }
        return list;
    }

    private List<String> orderLogisticsFieldCheck(JSONObject jsonObject){
        List<String> list;
        if(jsonObject.getJSONObject("data").getJSONObject("detail") == null ||
                jsonObject.getJSONObject("data").getJSONObject("detail").getString("expressCompanyId") == null ||
                jsonObject.getJSONObject("data").getJSONObject("detail").getString("expressCompanyId").equals("rgwl")){
            list = this.validate(jsonObject.getJSONObject("data").toJavaObject(LogisticsDto.class), LogisticsDto.RgwlGroup.class);
        }else{
            list = this.validate(jsonObject.getJSONObject("data").toJavaObject(LogisticsDto.class), LogisticsDto.OrderGroup.class);
        }

        return list;
    }

    // 订单重复检查
    private List<String> orderRepeatCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        EventDto eventDto = jsonObject.toJavaObject(EventDto.class);
        switch (eventDto.getTag()) {
            case "order":
                switch (eventDto.getEvent()) {
                    case "orderCreated":
                        list = geneOrderExistCheck(jsonObject);
                        list.addAll(localStaffJobNumberCheck(jsonObject));
                        break;
                    case "orderPayed":
                    case "sampleConfirmed":
                    case "testingReportUploaded":
                    case "integralGranted":
                    case "orderCancel":
                    case "orderRefunded":
                    case "orderLogistics":
                    case "orderUpdated":
                        list = geneOrderNotExistCheck(jsonObject);
                        list.addAll(localStaffJobNumberCheck(jsonObject));
                        break;
                    case "orderInvoiced":
                        list = geneInvoiceOrdersNotExistCheck(jsonObject);
                        break;
                    default:
                        break;
                }
                break;
            case "logistics":
                switch (eventDto.getEvent()) {
                    case "localStaffReimburseApproved":
                        list.addAll(localStaffJobNumberCheck(jsonObject));
                        break;
                    default:
                        break;
                }
                break;
            case "system":
                switch (eventDto.getEvent()) {
                    case "localStaffUpdate":
                        list.addAll(localStaffJobNumberCheck(jsonObject));
                        break;
                    case "doctorUpdate":
                        break;
                    case "provinceUpdate":
                        break;
                    case "cityUpdate":
                        break;
                    case "countyUpdate":
                        break;
                    case "regionUpdate":
                        break;
                    case "regionProvinceUpdate":
                        list = regionProvinceNotExistCheck(jsonObject);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        return list;
    }

    private List<String> geneOrderExistCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        String orderId = jsonObject.getJSONObject("data").getString("orderId");
        if(orderId != null){
            GeneDetailBaseEntity detail = geneDetailMapper.getGeneDetailByOrderId(orderId);
            if(detail != null){
                list.add("订单已存在，orderId：" + orderId);
            }
        }
        return list;
    }

    private List<String> geneOrderNotExistCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        String orderId = jsonObject.getJSONObject("data").getString("orderId");
        if(orderId != null){
            GeneDetailBaseEntity detail = geneDetailMapper.getGeneDetailByOrderId(orderId);
            if(detail == null){
                list.add("订单不存在，orderId：" + orderId);
            }
        }
        return list;
    }

    private List<String> geneInvoiceOrdersNotExistCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("orderIds");
        if(jsonArray != null){
            for(int i=0; i<jsonArray.size(); i++){
                String orderId = jsonArray.getString(i);
                GeneDetailBaseEntity detail = geneDetailMapper.getGeneDetailByOrderId(orderId);
                if(detail == null){
                    list.add("订单不存在，orderId：" + orderId);
                }
            }
        }
        return list;
    }

    private List<String> regionProvinceNotExistCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        String provinceId = jsonObject.getJSONObject("data").getString("provinceId");
        String regionId = jsonObject.getJSONObject("data").getString("regionId");

        if(regionId != null && provinceId != null){
            RegionEntity region = regionMapper.findRegionById(regionId);
            ProvinceEntity province = provinceMapper.findProvinceById(provinceId);
            if(region == null){
                list.add("大区不存在，regionId：" + regionId);
            }
            if(province == null){
                list.add("省份不存在，provinceId：" + provinceId);
            }
        }

        return list;
    }

    private List<String> localStaffJobNumberCheck(JSONObject jsonObject){
        List<String> list = new ArrayList<>();

        String localStaffId = jsonObject.getJSONObject("data").getString("localStaffId");
        String localStaffJobNumber = jsonObject.getJSONObject("data").getString("localStaffJobNumber");

        if(localStaffId != null && localStaffJobNumber != null){
            LocalStaffEntity entity = new LocalStaffEntity();
            entity.setLocalStaffId(localStaffId);
            entity.setLocalStaffJobNumber(localStaffJobNumber);
            Long count = localStaffMapper.getCountWhenJobNumberEqualsButIdNotEquals(entity);
            if(count > 0){
                //list.add("localStaffJobNumber重复 ： "+ localStaffJobNumber);
            }
        }

        return list;
    }
}
