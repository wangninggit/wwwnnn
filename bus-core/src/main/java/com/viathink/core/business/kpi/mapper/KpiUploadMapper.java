package com.viathink.core.business.kpi.mapper;

import com.viathink.core.business.kpi.bean.KpiUploadEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface KpiUploadMapper {
    void addUploadExcel(KpiUploadEntity entity);
    KpiUploadEntity findRecordById(Long id);
}
