package com.viathink.core.business.gene.mapper;

import com.viathink.core.batch.dto.ProfitReportDto;
import com.viathink.core.batch.dto.ProvinceSnapshootRecordDto;
import com.viathink.core.batch.dto.RegionSnapshootRecordDto;
import com.viathink.core.business.gene.bean.GeneDetailBaseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneHalfYearlyDetailMapper {
    void addGeneHalfYearlyDetail(GeneDetailBaseEntity GeneHalfYearlyDetailEntity);
    void updateGeneHalfYearlyDetail(GeneDetailBaseEntity GeneHalfYearlyDetailEntity);
    GeneDetailBaseEntity getGeneHalfYearlyDetailById(Long id);
    GeneDetailBaseEntity getGeneHalfYearlyDetailByOrderId(String orderId);
    GeneDetailBaseEntity getGeneHalfYearlyDetailByOrderIdAndHalfYearStr(GeneDetailBaseEntity GeneHalfYearlyDetailEntity);
    void updateGeneHalfYearlyDetailByOrderIdAndHalfYearStr(GeneDetailBaseEntity GeneHalfYearlyDetailEntity);
    List<ProfitReportDto> getProfitByHalfYearStr(String halfYearStr);
    ProfitReportDto countProfitByHalfYearStr(String halfYearStr);
    List<RegionSnapshootRecordDto> getSnapshootRecordByHalfYearlyStrAndGroupByRegionId(String halfYearlyStr);
    List<ProvinceSnapshootRecordDto> getSnapshootRecordByHalfYearlyStrAndGroupByProvinceId(String halfYearlyStr);
}
