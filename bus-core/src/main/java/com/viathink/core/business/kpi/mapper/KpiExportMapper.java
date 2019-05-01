package com.viathink.core.business.kpi.mapper;

import com.viathink.core.business.kpi.bean.KpiExportEntity;
import com.viathink.core.business.kpi.bean.KpiUploadEntity;
import com.viathink.core.business.kpi.dto.KpiHistoryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiExportMapper {
    void addExportExcel(KpiExportEntity entity);
    List<KpiHistoryDto> getExportHistory();
    KpiExportEntity findRecordById(Long id);
}
