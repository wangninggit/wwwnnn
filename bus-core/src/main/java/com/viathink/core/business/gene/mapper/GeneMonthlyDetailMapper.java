package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.BusinessDetailResult;
import com.viathink.core.batch.dto.ProfitReportDto;
import com.viathink.core.batch.dto.ProvinceSnapshootRecordDto;
import com.viathink.core.batch.dto.RegionSnapshootRecordDto;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import com.viathink.core.business.gene.dto.DashboardOrderTrendDto;
import com.viathink.core.business.gene.dto.DateRangeStrDto;
import com.viathink.core.business.kpi.dto.KpiResultDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneMonthlyDetailMapper {
    void addGeneMonthlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneMonthlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    GeneDetailBaseEntity getGeneMonthlyDetailById(Long id);
    GeneDetailBaseEntity getGeneMonthlyDetailByOrderId(String orderId);
    GeneDetailBaseEntity getGeneMonthlyDetailByOrderIdAndMonthStr(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneMonthlyDetailByOrderIdAndMonthStr(GeneDetailBaseEntity GeneDetailBaseEntity);
    List<BusinessDetailResult> getHalfYearDetailReportGroupByMonthStr(String halfYearStr);
    BusinessDetailResult countHalfYearDetailReportByHalfYearStr(String halfYearStr);
    List<BusinessDetailResult> getYearDetailReportGroupByMonthStr(String monthStr);
    BusinessDetailResult countYearDetailReportByYearStr(String monthStr);
    List<ProfitReportDto> getProfitByMonthStr(String monthStr);
    ProfitReportDto countProfitByMonthStr(String monthStr);
    List<RegionSnapshootRecordDto> getSnapshootRecordByMonthlyStrAndGroupByRegionId(String monthlyStr);
    List<ProvinceSnapshootRecordDto> getSnapshootRecordByMonthlyStrAndGroupByProvinceId(String monthlyStr);
    List<DashboardOrderTrendDto> queryOrderTrendYear(DateRangeStrDto dateRangeStrDto);
    List<KpiResultDto> getMonthKpiByLocalStaffId(String localStaffId);
}
