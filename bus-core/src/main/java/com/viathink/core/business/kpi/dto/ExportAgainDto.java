package com.viathink.core.business.kpi.dto;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Data
public class ExportAgainDto {
    private static final long serialVersionUID = 1L;
    private XSSFWorkbook wb;
    private String fileName;
}

