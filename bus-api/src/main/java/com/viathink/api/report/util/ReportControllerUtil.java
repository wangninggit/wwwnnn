package com.viathink.api.report.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.api.report.dto.ReportDetailParamDto;
import com.viathink.core.batch.dto.RecordObjectDto;
import com.viathink.core.business.gene.bean.SnapshootEntity;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.util.BatchUtil;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ReportControllerUtil {

    private static Boolean dateStrFormatCheck(String dateStr, String timeRange) {
        String dataFormat;
        String[] halfYearStrArray = {"h1","h2"};
        String[] quarterStrArray = {"q1","q2","q3","q4"};
        if(dateStr == null || timeRange == null){
            return false;
        }
        String[] split = dateStr.split("-");
        switch (timeRange) {
            case BatchUtil.TIME_RANGE_DAY_STR:
                if(split.length == 3){
                    dataFormat = "yyyy-MM-dd";
                }else{
                    return false;
                }
                break;
            case BatchUtil.TIME_RANGE_MONTH_STR:
                if(split.length == 2){
                    dataFormat = "yyyy-MM";
                }else{
                    return false;
                }
                break;
            case BatchUtil.TIME_RANGE_YEAR_STR:
                if(split.length == 1){
                    dataFormat = "yyyy";
                }else{
                    return false;
                }
                break;
            case BatchUtil.TIME_RANGE_HALF_YEAR_STR:
                if((split.length == 2) && (Arrays.asList(halfYearStrArray).contains(split[1]))){
                    dateStr = split[0];
                    dataFormat = "yyyy";
                }else{
                    return false;
                }
                break;
            case BatchUtil.TIME_RANGE_QUARTER_STR:
                if((split.length == 2) && (Arrays.asList(quarterStrArray).contains(split[1]))){
                    dateStr = split[0];
                    dataFormat = "yyyy";
                }else{
                    return false;
                }
                break;
            default:
                return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private static JSONObject reportResult(SnapshootEntity snapshootByClass,SnapshootService service) {
        if(snapshootByClass != null){
            return JSONObject.parseObject(snapshootByClass.getRecord());
        }else{
            return JSON.parseObject(JSON.toJSONString(new RecordObjectDto(), SerializerFeature.WriteMapNullValue));
        }
    }

    public static JSONObject getSnapshootByClass(SnapshootService service, ReportDetailParamDto reportDetailParamDto, String type){
        if(!dateStrFormatCheck(reportDetailParamDto.getDateStr(),reportDetailParamDto.getTimeDimension())){
            return null;
        }
        SnapshootEntity snapshootEntity = new SnapshootEntity();
        String timeRange = reportDetailParamDto.getTimeDimension();
        String dateStr = reportDetailParamDto.getDateStr();
        snapshootEntity.setTimeRange(timeRange);
        switch (reportDetailParamDto.getTimeDimension()) {
            case BatchUtil.TIME_RANGE_DAY_STR:
                snapshootEntity.setDayStr(dateStr);
                break;
            case BatchUtil.TIME_RANGE_MONTH_STR:
                snapshootEntity.setMonthStr(dateStr);
                break;
            case BatchUtil.TIME_RANGE_HALF_YEAR_STR:
                snapshootEntity.setHalfYearStr(dateStr);
                break;
            case BatchUtil.TIME_RANGE_YEAR_STR:
                snapshootEntity.setYearStr(dateStr);
                break;
            default:
                throw new ParamsInvalidException(1006,"请求参数错误");
        }

        snapshootEntity.setType(type);
        SnapshootEntity snapshootByClass = service.findSnapshootByClass(snapshootEntity);
        return reportResult(snapshootByClass,service);
    }
}
