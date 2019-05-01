package com.viathink.core.business.kpi.service;

import com.viathink.core.business.kpi.dto.ExportAgainDto;
import com.viathink.core.business.kpi.dto.ExportHistoryParamDto;
import com.viathink.core.business.kpi.dto.ExportHistoryResultDto;
import com.viathink.core.business.kpi.dto.KpiHistoryDto;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public interface KpiService {
    XSSFWorkbook uploadAndExportKpi(String uploadFileName, byte[] file , String exportFileName ,Long kpiDate) throws IOException;
    List<KpiHistoryDto> exportHistory();
    ExportHistoryResultDto exportHistoryFile(ExportHistoryParamDto paramDto);
    ExportAgainDto exportAgain(Long id);
}
