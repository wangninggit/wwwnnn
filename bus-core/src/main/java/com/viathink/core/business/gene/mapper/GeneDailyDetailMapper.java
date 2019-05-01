package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.*;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneDailyDetailMapper {
    void addGeneDailyDetail(GeneDetailBaseEntity geneDailyDetailEntity);
    void updateGeneDailyDetail(GeneDetailBaseEntity geneDailyDetailEntity);
    GeneDetailBaseEntity getGeneDailyDetailById(Long id);
    GeneDetailBaseEntity getGeneDailyDetailByOrderId(String orderId);
    void updateGeneDailyDetailByDayStrAndOrderId(GeneDetailBaseEntity geneDailyDetailEntity);
    GeneDetailBaseEntity getGeneDailyDetailByOrderIdAndDailyStr(GeneDetailBaseEntity geneDailyDetailEntity);
    List<DetailResultDto> getDailyDetailReportByDayStr(String dayStr);
    BusinessDetailResult countDailyDetailReportByDayStr(String dayStr);
    List<BusinessDetailResult> getMonthDetailReportGroupByDayStr(String monthStr);
    BusinessDetailResult countMonthDetailReportByMonthStr(String monthStr);
    List<RegionSnapshootRecordDto> getSnapshootRecordByDailyStrAndGroupByRegionId(String dailyStr);
    List<ProvinceSnapshootRecordDto> getSnapshootRecordByDailyStrAndGroupByProvinceId(String dailyStr);
    List<RegionSnapshootRecordDto> getSnapshootRecordByDailyRangeAndRegionIdsAndGroupByRegionId(QueryRegionContrastParamDto queryRegionContrastParamDto);
    List<BusinessDetailResult> queryDailyDetail(DetailCountForm detailQueryForm);
    BusinessDetailResult queryDailyDetailCount(DetailCountForm detailQueryForm);
    List<DetailResultDto> getDailyDetailList(DetailQueryForm form);
    List<ProvinceSnapshootRecordDto> queryBusinessProvince(ProvinceQueryForm provinceQueryForm);
    List<ProvinceSnapshootRecordDto> queryBusinessProvinceWeek(DateRangeStrDto dateRangeStrDto);
    List<ProvinceSnapshootRecordDto> queryBusinessProvinceMonth(String monthStr);
    List<ProvinceSnapshootRecordDto> queryBusinessProvinceYear(String yearStr);
    List<OrderCountRankDto> getOrderCountByDateDimensionGroupByRegionId(QueryRegionContrastParamDto queryRegionContrastParamDto);
    List<DashboardOrderTrendDto> queryOrderTrendWeek(DateRangeStrDto dateRangeStrDto);
    DashboardOrderTrendDto queryOrderTrendHour(DateRangeDto dateRangeDto);
    List<DashboardOrderTrendDto> queryOrderTrendHourGroup(DateRangeDto dateRangeDto);
    List<DetailResultDto> getDailyDetailExcelList(DetailCountForm detailQueryForm);
    OrderTrendMonthDto getOrderTrendMonthAvg(OrderTrendMonthParamDto orderTrendMonthParamDto);
}
