package com.viathink.core.business.gene.service;

import com.alibaba.fastjson.JSONObject;
import com.viathink.core.batch.dto.BusinessResult;
import com.viathink.core.business.gene.dto.*;
import com.viathink.core.user.dto.PageParam;

import java.util.List;

public interface GeneDailyDetailService {
    BusinessResult queryDailyDetailReport(DetailCountForm detailQueryForm);
    JSONObject queryBusinessProvinceReport(ProvinceQueryForm provinceQueryForm);
    DetailPageInfoDto<DetailResultDto> getDailyDetailList(DetailQueryForm form);
    JSONObject queryBussinessRegionContrast(QueryRegionContrastParamDto queryRegionContrastParamDto);
    JSONObject queryBussinessTestingItemContrast(QueryTestingItemContrastParamDto queryTestingItemContrastParamDto);
    JSONObject getDailyDetailExcelList(DetailCountForm form);
    List<OrderWithoutInvoiceDto> getOrderWithoutInvoice(OrderWithoutInvoiceForm form);
    List<OrderWithoutInvoiceDto> getOrderWithoutInvoiceByPage(OrderWithoutInvoiceForm form,PageParam pageParam,Boolean isPage);
}
