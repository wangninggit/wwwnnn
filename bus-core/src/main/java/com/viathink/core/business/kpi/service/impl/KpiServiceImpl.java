package com.viathink.core.business.kpi.service.impl;

import com.viathink.core.business.gene.bean.LocalStaffEntity;
import com.viathink.core.business.gene.mapper.GeneMonthlyDetailMapper;
import com.viathink.core.business.gene.mapper.LocalStaffMapper;
import com.viathink.core.business.kpi.bean.KpiExportEntity;
import com.viathink.core.business.kpi.bean.KpiUploadEntity;
import com.viathink.core.business.kpi.dto.*;
import com.viathink.core.business.kpi.mapper.KpiExportMapper;
import com.viathink.core.business.kpi.mapper.KpiUploadMapper;
import com.viathink.core.business.kpi.service.KpiService;
import com.viathink.core.business.kpi.util.KpiUtils;
import com.viathink.core.util.FileUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class KpiServiceImpl implements KpiService {
    private final LocalStaffMapper localStaffMapper;
    private final GeneMonthlyDetailMapper geneMonthlyDetailMapper;
    private final KpiUploadMapper kpiUploadMapper;
    private final KpiExportMapper kpiExportMapper;

    @Value("${sys.kpi.local.path}")
    String rootPath;

    @Autowired
    public KpiServiceImpl(LocalStaffMapper localStaffMapper, GeneMonthlyDetailMapper geneMonthlyDetailMapper, KpiUploadMapper kpiUploadMapper, KpiExportMapper kpiExportMapper) {
        this.localStaffMapper = localStaffMapper;
        this.geneMonthlyDetailMapper = geneMonthlyDetailMapper;
        this.kpiUploadMapper = kpiUploadMapper;
        this.kpiExportMapper = kpiExportMapper;
    }

    @Override
    public XSSFWorkbook uploadAndExportKpi(String uploadFileName, byte[] file, String exportFileName, Long kpiDate) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(file));
        // 保存上传文件
        String uploadUUIDFileName = FileUtil.saveWorkbook(wb, rootPath + "kpi/upload/");
        if (uploadUUIDFileName == null)
            return null;

        // 生成excel
        wb = generateKpiExcel(wb, kpiDate);

        // 保存excel
        String exportUUIDFileName = FileUtil.saveWorkbook(wb, rootPath + "kpi/export/");

        // 存库
        KpiUploadEntity kpiUploadEntity = new KpiUploadEntity();
        kpiUploadEntity.setOriginalFileName(uploadFileName);
        kpiUploadEntity.setStorageFileName(uploadUUIDFileName);
        kpiUploadMapper.addUploadExcel(kpiUploadEntity);

        KpiExportEntity kpiExportEntity = new KpiExportEntity();
        kpiExportEntity.setOriginalFileName(exportFileName);
        kpiExportEntity.setStorageFileName(exportUUIDFileName);
        kpiExportEntity.setKpiDate(kpiDate);
        kpiExportEntity.setKpiModule(kpiUploadEntity.getId());
        kpiExportMapper.addExportExcel(kpiExportEntity);

        return wb;
    }

    @Override
    public List<KpiHistoryDto> exportHistory() {
        return kpiExportMapper.getExportHistory();
    }

    @Override
    public ExportHistoryResultDto exportHistoryFile(ExportHistoryParamDto paramDto) {
        ExportHistoryResultDto resultDto = new ExportHistoryResultDto();
        if(paramDto.getType().equals(KpiUtils.HISTORY_TYPE_UPLOAD)){
            KpiUploadEntity record = kpiUploadMapper.findRecordById(paramDto.getId());
            resultDto.setFileName(record.getOriginalFileName());
            resultDto.setSourceFile(rootPath + "kpi/upload/" + record.getStorageFileName());
        }else{
            KpiExportEntity record = kpiExportMapper.findRecordById(paramDto.getId());
            resultDto.setFileName(record.getOriginalFileName());
            resultDto.setSourceFile(rootPath + "kpi/export/" + record.getStorageFileName());
        }
        return resultDto;
    }

    @Override
    public ExportAgainDto exportAgain(Long id) {
        KpiExportEntity exportRecord = kpiExportMapper.findRecordById(id);
        KpiUploadEntity uploadRecord = kpiUploadMapper.findRecordById(exportRecord.getKpiModule());

        try {
            InputStream inputStream = new FileInputStream(rootPath + "kpi/upload/" + uploadRecord.getStorageFileName());
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            wb = generateKpiExcel(wb, exportRecord.getKpiDate());
            String exportUUIDFileName = FileUtil.saveWorkbook(wb, rootPath + "kpi/export/");
            KpiExportEntity kpiExportEntity = new KpiExportEntity();
            kpiExportEntity.setOriginalFileName(exportRecord.getOriginalFileName());
            kpiExportEntity.setStorageFileName(exportUUIDFileName);
            kpiExportEntity.setKpiDate(exportRecord.getKpiDate());
            kpiExportEntity.setKpiModule(exportRecord.getId());
            kpiExportMapper.addExportExcel(kpiExportEntity);

            ExportAgainDto dto = new ExportAgainDto();
            dto.setFileName(exportRecord.getOriginalFileName());
            dto.setWb(wb);
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private XSSFWorkbook generateKpiExcel(XSSFWorkbook wb, Long kpiDate) {

        XSSFSheet sheetExample = wb.getSheetAt(0);
        for (int i = 3; i <= sheetExample.getLastRowNum(); i++) {
            XSSFRow row = sheetExample.getRow(i);
            String jobNumber;
            if(row.getCell(0).getCellTypeEnum().equals(CellType.NUMERIC))
                jobNumber = (long) row.getCell(0).getNumericCellValue() + "";
            else
                jobNumber = row.getCell(0).getStringCellValue();

            if(jobNumber.equals(""))
                continue;

            List<LocalStaffEntity> localStaffList = localStaffMapper.findLocalStaffByJobNumber(jobNumber);
            if (localStaffList.size() != 1)
                continue;
            LocalStaffEntity localStaff = localStaffList.get(0);

            row.getCell(1).setCellValue(localStaff.getLocalStaffName());
            row.getCell(2).setCellValue(localStaff.getLocalStaffRegionName());
            row.getCell(3).setCellValue(localStaff.getLocalStaffProvinceName());
            row.getCell(4).setCellValue(localStaff.getLocalStaffCityName());

            List<KpiResultDto> kpiResultList = geneMonthlyDetailMapper.getMonthKpiByLocalStaffId(localStaff.getLocalStaffId());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(kpiDate));
            List<Long> kpiList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                KpiResultDto kpiResult = null;

                cal.add(Calendar.MONTH, 0 - j);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
                String format = formater.format(cal.getTime());

                for (KpiResultDto tmp : kpiResultList) {
                    if (tmp.getMonthStr().equals(format))
                        kpiResult = tmp;
                }
                Long kpi = (kpiResult == null) ? 0 : (kpiResult.getKpi());
                kpiList.add(kpi);
            }

            row.getCell(5).setCellValue(kpiList.get(0) / 1000.0);
            row.getCell(6).setCellValue(kpiList.get(1) / 1000.0);
            row.getCell(8).setCellValue((kpiList.get(1) - kpiList.get(2)) / 1000.0);

            row.getCell(7).setCellFormula(row.getCell(7).getCellFormula());
            row.getCell(9).setCellFormula(row.getCell(9).getCellFormula());
            row.getCell(12).setCellFormula(row.getCell(12).getCellFormula());
        }
        return wb;
    }
}
