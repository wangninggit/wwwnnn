package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.ProfitReportDto;
import com.viathink.core.batch.dto.ProvinceSnapshootRecordDto;
import com.viathink.core.batch.dto.RegionSnapshootRecordDto;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneYearlyDetailMapper {
    void addGeneYearlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneYearlyDetail(GeneDetailBaseEntity GeneDetailBaseEntity);
    GeneDetailBaseEntity getGeneYearlyDetailById(Long id);
    GeneDetailBaseEntity getGeneYearlyDetailByOrderId(String orderId);
    GeneDetailBaseEntity getGeneYearlyDetailByOrderIdAndYearStr(GeneDetailBaseEntity GeneDetailBaseEntity);
    void updateGeneYearlyDetailByOrderIdAndYearStr(GeneDetailBaseEntity GeneDetailBaseEntity);
    List<ProfitReportDto> getProfitByYearStr(String yearStr);
    ProfitReportDto countProfitByYearStr(String yearStr);
    List<RegionSnapshootRecordDto> getSnapshootRecordByYearlyStrAndGroupByRegionId(String yearlyStr);
    List<ProvinceSnapshootRecordDto> getSnapshootRecordByYearlyStrAndGroupByProvinceId(String yearlyStr);
}
