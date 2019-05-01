package com.viathink.api.query.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.api.util.SortControllerUtil;
import com.viathink.core.batch.dto.BusinessResult;
import com.viathink.core.batch.dto.OrderTrendMonthResultDto;
import com.viathink.core.business.gene.bean.OrderLogisticsEntity;
import com.viathink.core.business.gene.dto.*;
import com.viathink.core.business.gene.service.GeneDailyDetailService;
import com.viathink.core.business.gene.service.IncomeReportService;
import com.viathink.core.business.gene.service.OrderLogisticsService;
import com.viathink.core.business.gene.service.SnapshootService;
import com.viathink.core.user.dto.PageParam;
import com.viathink.core.util.BatchUtil;
import com.viathink.core.util.ExpressTrackingUtil;
import com.viathink.core.util.ReadResourceJsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GeneTestingController {
    @Value("${sys.logistics.hearValue}")
    private String hearValue;
    private Logger logger = LoggerFactory.getLogger(GeneTestingController.class);

    private final SnapshootService snapshootService;
    private final IncomeReportService incomeReportService;
    private final GeneDailyDetailService geneDailyDetailService;
    private final OrderLogisticsService orderLogisticsService;

    @Autowired
    public GeneTestingController(SnapshootService snapshootService, IncomeReportService incomeReportService, GeneDailyDetailService geneDailyDetailService, OrderLogisticsService orderLogisticsService) {
        this.snapshootService = snapshootService;
        this.incomeReportService = incomeReportService;
        this.geneDailyDetailService = geneDailyDetailService;
        this.orderLogisticsService = orderLogisticsService;
    }

    @RequiresPermissions("query:income-detail")
    @GetMapping(value = "/query/income-detail")
    public PageInfo<IncomeOrderDto> getGeneDetailIncomeReportByPage(DateRangeDto dateRangeDto, PageParam pageParam) {
        List<IncomeOrderDto> list = incomeReportService.getGeneDetailIncomeReportByPage(dateRangeDto,pageParam,true);
        return new PageInfo<>(list);
    }

    @RequiresPermissions("query:daily-detail")
    @GetMapping(value = "/query/daily-detail")
    public BusinessResult queryDailyDetail(@Valid DetailCountForm detailQueryForm){
        return geneDailyDetailService.queryDailyDetailReport(detailQueryForm);
    }

    @RequiresPermissions("query:province-contrast")
    @GetMapping(value = "/query/province-contrast")
    public JSONObject queryProvinceDetail(@Validated ProvinceQueryForm provinceQueryForm){
        return geneDailyDetailService.queryBusinessProvinceReport(provinceQueryForm);
    }
    @RequiresPermissions("query:region-contrast")
    @GetMapping(value = "/query/region-contrast")
    public JSONObject getRegionContrastReport(@Validated QueryRegionContrastParamDto queryRegionContrastParamDto) {
        return geneDailyDetailService.queryBussinessRegionContrast(queryRegionContrastParamDto);
    }

    @RequiresPermissions("query:business-detail")
    @GetMapping(value = "/query/business-detail")
    public DetailPageInfoDto<DetailResultDto> queryBusinessDetail(@Valid DetailQueryForm form) {
        return geneDailyDetailService.getDailyDetailList(form);
    }

    @RequiresPermissions("query:order-without-invoice")
    @GetMapping(value = "/query/order-without-invoice")
    public PageInfo<OrderWithoutInvoiceDto> queryOrderWithoutInvoice(@Valid OrderWithoutInvoiceForm form,PageParam pageParam) {
        List<OrderWithoutInvoiceDto> orderWithoutInvoiceDtoList = geneDailyDetailService.getOrderWithoutInvoiceByPage(form,pageParam,true);
        return new PageInfo<>(orderWithoutInvoiceDtoList);
    }

    @RequiresPermissions("query:testing-item-contrast")
    @GetMapping(value = "/query/testing-item-contrast")
    public JSONObject getTestingItemContrastReport(@Validated QueryTestingItemContrastParamDto queryTestingItemContrastParamDto) {
        // 参数校验
        SortControllerUtil.SortControllerParamCheck(queryTestingItemContrastParamDto);
        return geneDailyDetailService.queryBussinessTestingItemContrast(queryTestingItemContrastParamDto);
    }

    @RequiresPermissions("query:order-trend-month-avg")
    @GetMapping(value = "/query/month/order-trend")
    public PageInfo<OrderTrendMonthResultDto> getOrderTrendMonthAvgReport(@Valid DateRangeStrDto dateRangeStrDto, PageParam pageParam) {
        dateRangeStrDto.setDate(BatchUtil.TYPE_GENE_ORDER_TREND_DETAIL);
        List<OrderTrendMonthResultDto> list = snapshootService.findSnapshootByMonthAvg(dateRangeStrDto,pageParam,true);
        return new PageInfo<>(list);
    }

    @RequiresUser
    @GetMapping(value = "/query/order-logistics/{id}")
    public JSONObject getOrderLogisticsByOrderId(@PathVariable String id) {
        JSONObject jsonObject =  orderLogisticsService.findOrderLogisticsByOrderId(id);
        if(jsonObject==null){
            throw new ParamsInvalidException(6003,"该订单不存在");
        }
        return jsonObject;
    }

    @RequiresUser
    @GetMapping(value = "/query/order-logistics")
    public JSONObject getOrderLogistics(@Valid OrderLogisticsEntity orderLogisticsEntity) {
        JSONObject jsonObject = ReadResourceJsonUtil.parseStrToJSONObject("orderLogistics.json");
        if(jsonObject == null){
            logger.error("orderLogistics.json 文件不存在");
            throw new RuntimeException("orderLogistics.json 文件不存在");
        }
        String expressCompanyId = jsonObject.getString(orderLogisticsEntity.getExpressCompanyId());
        if(expressCompanyId==null){
            throw new ParamsInvalidException(6004,"该物流公司不存在");
        }
        String expressNumber  = orderLogisticsEntity.getExpressNumber();
        return ExpressTrackingUtil.expressTracking(expressCompanyId,expressNumber,hearValue);
    }
}
