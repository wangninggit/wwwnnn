package com.viathink.api.export.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.viathink.api.report.dto.ReportDetailParamDto;
import com.viathink.api.report.util.ReportControllerUtil;
import com.viathink.api.util.ExcelUtil;
import com.viathink.api.util.ExportControllerUtil;
import com.viathink.api.util.SortControllerUtil;
import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.business.gene.dto.*;
import com.viathink.core.business.gene.service.GeneDailyDetailService;
import com.viathink.core.business.gene.service.IncomeReportService;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.BusinessDateUtil;
import com.viathink.core.util.ExportExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportController {
    private Logger logger = LoggerFactory.getLogger(ExportController.class);
    private final IncomeReportService incomeReportService;
    private final GeneDailyDetailService geneDailyDetailService;
    private final SnapshootService snapshootService;

    @Autowired
    public ExportController(IncomeReportService incomeReportService, GeneDailyDetailService geneDailyDetailService, SnapshootService snapshootService) {
        this.incomeReportService = incomeReportService;
        this.geneDailyDetailService = geneDailyDetailService;
        this.snapshootService = snapshootService;
    }

    @RequiresPermissions("query:income-detail-export")
    @GetMapping(value = "/query/income-detail")
    public String getGeneDetailIncomeReportByPage(@Valid DateRangeDto dateRangeDto, PageParam pageParam, HttpServletResponse response) {
        List<IncomeOrderDto> list = incomeReportService.getGeneDetailIncomeReportByPage(dateRangeDto, pageParam, false);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(map));
        try {
            String filename = "查询-收入明细(" +
                    BusinessDateUtil.getBusinessDate(dateRangeDto.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(dateRangeDto.getEndDate()).getDayStr() + ").xlsx";

            ExcelUtil.incomeExcel(jsonObject, ExcelUtil.EXCEL_NAME_INCOME_DETAIL, response, filename);

            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/income-detail”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:daily-detail-export")
    @GetMapping(value = "/query/daily-detail")
    public String queryDailyDetail(@Valid DetailCountForm detailQueryForm, HttpServletResponse response) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(geneDailyDetailService.queryDailyDetailReport(detailQueryForm)));
            String filename = "查询-日统计(" +
                    BusinessDateUtil.getBusinessDate(detailQueryForm.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(detailQueryForm.getEndDate()).getDayStr() + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_DAILY_COUNT);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/daily-detail”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:province-contrast-export")
    @GetMapping(value = "/query/province-contrast")
    public String queryProvinceDetail(@Validated ProvinceQueryForm provinceQueryForm, HttpServletResponse response) {
        try {
            JSONObject jsonObject = geneDailyDetailService.queryBusinessProvinceReport(provinceQueryForm);
            String filename = "查询-省份对比(" +
                    BusinessDateUtil.getBusinessDate(provinceQueryForm.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(provinceQueryForm.getEndDate()).getDayStr() + ").xlsx";

            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_PROVINCE_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/province-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:region-contrast-export")
    @GetMapping(value = "/query/region-contrast")
    public String getRegionContrastReport(@Validated QueryRegionContrastParamDto queryRegionContrastParamDto, HttpServletResponse response) {
        try {
            JSONObject jsonObject = geneDailyDetailService.queryBussinessRegionContrast(queryRegionContrastParamDto);
            String filename = "查询-大区对比(" +
                    BusinessDateUtil.getBusinessDate(queryRegionContrastParamDto.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(queryRegionContrastParamDto.getEndDate()).getDayStr() + ").xlsx";

            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_REGION_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/region-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:business-detail-export")
    @GetMapping(value = "/query/business-detail")
    public String queryBusinessDetail(@Valid DetailCountForm form, HttpServletResponse response) {
        try {
            JSONObject jsonObject = geneDailyDetailService.getDailyDetailExcelList(form);
            String filename = "查询-业务明细(" +
                    BusinessDateUtil.getBusinessDate(form.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(form.getEndDate()).getDayStr() + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_BUSINESS_DETAIL);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/business-detail”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:testing-item-contrast-export")
    @GetMapping(value = "/query/testing-item-contrast")
    public String getTestingItemContrastReport(@Validated QueryTestingItemContrastParamDto queryTestingItemContrastParamDto, HttpServletResponse response) {
        // 参数校验
        SortControllerUtil.SortControllerParamCheck(queryTestingItemContrastParamDto);
        try {
            JSONObject jsonObject = geneDailyDetailService.queryBussinessTestingItemContrast(queryTestingItemContrastParamDto);
            String filename = "查询-检测项目对比(" +
                    BusinessDateUtil.getBusinessDate(queryTestingItemContrastParamDto.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(queryTestingItemContrastParamDto.getEndDate()).getDayStr() + ").xlsx";

            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_TESTING_ITEM_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/testing-item-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:order-without-invoice-export")
    @GetMapping(value = "/query/order-without-invoice")
    public String queryOrderWithoutInvoice(@Valid OrderWithoutInvoiceForm form,PageParam pageParam, HttpServletResponse response) {
        try {
            List<OrderWithoutInvoiceDto> orderWithoutInvoice = geneDailyDetailService.getOrderWithoutInvoiceByPage(form,pageParam,false);
            String filename = "查询-未开票明细表(" +
                    BusinessDateUtil.getBusinessDate(form.getStartDate()).getDayStr() + "~" +
                    BusinessDateUtil.getBusinessDate(form.getEndDate()).getDayStr() + ").xlsx";

            Map<String,Object> map = new HashMap<>();
            map.put("list",orderWithoutInvoice);
            ExcelUtil.incomeExcel(JSONObject.parseObject(JSONObject.toJSONString(map)), ExportExcelUtil.EXCEL_NAME_QUERY_ORDER_WITHOUT_INVOICE, response, filename);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/order-without-invoice”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:business-detail-export")
    @GetMapping(value = "/reports/business-detail")
    public String getBusinessDetailReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        try {
            JSONObject jsonObject = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_BUSINESS_DETAIL);
            String dateStr = reportDetailParamDto.getDateStr();

            if (reportDetailParamDto.getTimeDimension().equals(BatchUtil.TIME_RANGE_HALF_YEAR_STR)) {
                String[] split = dateStr.split("-h");
                dateStr = split[0] + ((split[1].equals("1") ? "上半年" : "下半年"));
            }

            String filename = "报表-业务明细表(" + dateStr + ").xlsx";
            XSSFWorkbook excel;
            switch (reportDetailParamDto.getTimeDimension()) {
                case BatchUtil.TIME_RANGE_DAY_STR:
                    excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORT_BUSINESS_DAILY_DETAIL);
                    break;
                case BatchUtil.TIME_RANGE_MONTH_STR:
                    excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORT_BUSINESS_MONTH_DETAIL);
                    break;
                default:
                    excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORT_BUSINESS_YEAR_DETAIL);
                    break;
            }
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/business-detail”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:region-contrast-export")
    @GetMapping(value = "/reports/region-contrast")
    public String getRegionContrastReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        JSONObject jsonObject = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_REGION_CONTRAST);
        try {
            String dateStr = reportDetailParamDto.getDateStr();
            if (reportDetailParamDto.getTimeDimension().equals(BatchUtil.TIME_RANGE_HALF_YEAR_STR)) {
                String[] split = dateStr.split("-h");
                dateStr = split[0] + ((split[1].equals("1") ? "上半年" : "下半年"));
            }

            String filename = "报表-大区对比(" + dateStr + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORTS_REGION_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/region-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:profits-export")
    @GetMapping(value = "/reports/profits")
    public String getProfitDetailReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        JSONObject jsonObject = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_PROFIT);
        try {
            String dateStr = reportDetailParamDto.getDateStr();
            if (reportDetailParamDto.getTimeDimension().equals(BatchUtil.TIME_RANGE_HALF_YEAR_STR)) {
                String[] split = dateStr.split("-h");
                dateStr = split[0] + ((split[1].equals("1") ? "上半年" : "下半年"));
            }

            String filename = "报表-利润表(" + dateStr + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORTS_PROFITS);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/region-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:province-contrast-export")
    @GetMapping(value = "/reports/province-contrast")
    public String getProvinceContrastReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        JSONObject jsonObject = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_PROVINCE_CONTRAST);
        try {
            String dateStr = reportDetailParamDto.getDateStr();
            if (reportDetailParamDto.getTimeDimension().equals(BatchUtil.TIME_RANGE_HALF_YEAR_STR)) {
                String[] split = dateStr.split("-h");
                dateStr = split[0] + ((split[1].equals("1") ? "上半年" : "下半年"));
            }

            String filename = "报表-省份对比(" + dateStr + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_REPORTS_PROVINCE_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/province-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:testing-item-contrast-export")
    @GetMapping(value = "/reports/testing-item-contrast")
    public String getTestingItemContrastReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        SortControllerUtil.SortControllerParamCheck(reportDetailParamDto);
        JSONObject snapshoot = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_TESTING_ITEM_CONTRAST);
        JSONArray jsonArray = snapshoot.getJSONArray("list");
        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WriteClassName);
        List<JSONObject> list = JSONObject.parseArray(jsonStr, JSONObject.class);
        list = SortControllerUtil.testingItemListSort(list,reportDetailParamDto);
        JSONObject ret = new JSONObject();
        ret.put("list", list);
        ret.put("sum", snapshoot.get("sum"));
        try {
            String dateStr = reportDetailParamDto.getDateStr();
            if (reportDetailParamDto.getTimeDimension().equals(BatchUtil.TIME_RANGE_HALF_YEAR_STR)) {
                String[] split = dateStr.split("-h");
                dateStr = split[0] + ((split[1].equals("1") ? "上半年" : "下半年"));
            }

            String filename = "报表-检测项目对比(" + dateStr + ").xlsx";
            XSSFWorkbook excel = ExportExcelUtil.readExcel(ret, ExportExcelUtil.EXCEL_NAME_REPORTS_TESTING_ITEM_CONTRAST);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/testing-item-contrast”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("report:income-detail-export")
    @GetMapping(value = "/reports/income-detail")
    public String getIncomeDetailReport(@Valid ReportDetailParamDto reportDetailParamDto, HttpServletResponse response) {
        JSONObject jsonObject = ReportControllerUtil.getSnapshootByClass(snapshootService, reportDetailParamDto, BatchUtil.TYPE_GENE_INCOME_DETAIL);
        try {
            String dateStr = reportDetailParamDto.getDateStr();
            String filename = "报表-收入明细表(" + dateStr + ").xlsx";
            ExcelUtil.incomeExcel(jsonObject, ExcelUtil.EXCEL_NAME_INCOME_DETAIL, response, filename);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/reports/income-detail”；message：" + e.getMessage());
            return "failed";
        }
    }

    @RequiresPermissions("query:order-trend-month-avg-export")
    @GetMapping(value = "/query/month/order-trend")
    public String getSnapshootByMonthAvg(@Validated DateRangeStrDto dateRangeStrDto,PageParam pageParam,HttpServletResponse response) {
        dateRangeStrDto.setDate(BatchUtil.TYPE_GENE_ORDER_TREND_DETAIL);
        List<OrderTrendMonthResultDto> list = snapshootService.findSnapshootByMonthAvg(dateRangeStrDto,pageParam,false);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(map));
        try {
            String filename = "查询-月均销量趋势(" +
                    dateRangeStrDto.getStartDateStr() + "~" +
                    dateRangeStrDto.getEndDateStr()+ ").xlsx";

            XSSFWorkbook excel = ExportExcelUtil.readExcel(jsonObject, ExportExcelUtil.EXCEL_NAME_QUERY_ORDER_TREND_MONTH_AVG);
            ExportControllerUtil.exportExcelToResponse(response, filename, excel);
            return "success";
        } catch (Exception e) {
            logger.error("导出Excel失败：“/query/month/order-trend”；message：" + e.getMessage());
            return "failed";
        }
    }
}
