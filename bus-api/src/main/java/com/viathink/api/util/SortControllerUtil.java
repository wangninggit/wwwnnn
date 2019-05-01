package com.viathink.api.util;

import com.alibaba.fastjson.JSONObject;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.api.report.dto.ReportDetailParamDto;
import com.viathink.core.business.gene.dto.QueryTestingItemContrastParamDto;
import com.viathink.core.util.SortServiceUtil;

import java.util.Arrays;
import java.util.List;

public class SortControllerUtil {
    public static void SortControllerParamCheck(QueryTestingItemContrastParamDto param){
        if(!Arrays.asList(SortServiceUtil.SORT_BY).contains(param.getSortBy())){
            throw new ParamsInvalidException(1006,"请求参数错误");
        }
        if(!Arrays.asList(SortServiceUtil.SORT_MODE).contains(param.getSortMode())){
            throw new ParamsInvalidException(1006,"请求参数错误");
        }
    }

    public static void SortControllerParamCheck(ReportDetailParamDto param){
        if(!Arrays.asList(SortServiceUtil.SORT_BY).contains(param.getSortBy())){
            throw new ParamsInvalidException(1006,"请求参数错误");
        }
        if(!Arrays.asList(SortServiceUtil.SORT_MODE).contains(param.getSortMode())){
            throw new ParamsInvalidException(1006,"请求参数错误");
        }
    }

    public static List<JSONObject> testingItemListSort(List<JSONObject> list,ReportDetailParamDto reportDetailParamDto){
        list.sort((o1, o2) -> {
            Long c1, c2;
            Integer mode;
            if (reportDetailParamDto.getSortBy().equals(SortServiceUtil.SORT_BY[0])) {
                c1 = o1.getLong("testingItemPlaceCount");
                c2 = o2.getLong("testingItemPlaceCount");
            } else {
                c1 = o1.getLong("testingItemOrderIncome");
                c2 = o2.getLong("testingItemOrderIncome");
            }
            if (reportDetailParamDto.getSortMode().equals(SortServiceUtil.SORT_MODE[0])) {
                mode = 1;
            } else {
                mode = -1;
            }
            if (c1 > c2) {
                return -1 * mode;
            } else if (c1.equals(c2)) {
                return 0;
            } else {
                return mode;
            }
        });
        return list;
    }
}
