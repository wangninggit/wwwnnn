package com.viathink.api.report.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.api.report.dto.ReportDetailParamDto;
import com.viathink.api.report.util.ReportControllerUtil;
import com.viathink.api.util.SortControllerUtil;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.util.BatchUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneReportController {
    private final SnapshootService snapshootService;

    @Autowired
    public GeneReportController(SnapshootService snapshootService) {
        this.snapshootService = snapshootService;
    }

    @RequiresPermissions("report:business-detail")
    @GetMapping(value = "/reports/business-detail")
    public JSONObject getBusinessDetailReport(ReportDetailParamDto reportDetailParamDto) {
        return ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
    }

    @RequiresPermissions("report:region-contrast")
    @GetMapping(value = "/reports/region-contrast")
    public JSONObject getRegionContrastReport(ReportDetailParamDto reportDetailParamDto) {
        return ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_REGION_CONTRAST);
    }

    @RequiresPermissions("report:profits")
    @GetMapping(value = "/reports/profits")
    public JSONObject getProfitDetailReport(ReportDetailParamDto reportDetailParamDto) {
        return ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_PROFIT);
    }

    @RequiresPermissions("report:province-contrast")
    @GetMapping(value = "/reports/province-contrast")
    public JSONObject getProvinceContrastReport(ReportDetailParamDto reportDetailParamDto) {
        return ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
    }

    @RequiresPermissions("report:testing-item-contrast")
    @GetMapping(value = "/reports/testing-item-contrast")
    public JSONObject getTestingItemContrastReport(ReportDetailParamDto reportDetailParamDto) {
        SortControllerUtil.SortControllerParamCheck(reportDetailParamDto);
        JSONObject snapshoot = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        JSONArray jsonArray = snapshoot.getJSONArray("list");
        String jsonStr = JSONObject.toJSONString(jsonArray,SerializerFeature.WriteClassName);
        List<JSONObject> list = JSONObject.parseArray(jsonStr, JSONObject.class);
        list = SortControllerUtil.testingItemListSort(list,reportDetailParamDto);
        JSONObject ret = new JSONObject();
        ret.put("list",list);
        ret.put("sum",snapshoot.get("sum"));
        return ret;
    }

    @RequiresPermissions("report:income-detail")
    @GetMapping(value = "/reports/income-detail")
    public JSONObject getIncomeDetailReport(ReportDetailParamDto reportDetailParamDto) {
        return ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_INCOME_DETAIL);
    }

}
