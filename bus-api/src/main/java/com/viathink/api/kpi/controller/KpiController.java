package com.viathink.api.kpi.controller;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.api.util.ExportControllerUtil;
import com.viathink.api.util.KpiUtil;
import com.viathink.core.business.kpi.dto.ExportAgainDto;
import com.viathink.core.business.kpi.dto.ExportHistoryParamDto;
import com.viathink.core.business.kpi.dto.ExportHistoryResultDto;
import com.viathink.core.business.kpi.dto.KpiHistoryDto;
import com.viathink.core.business.kpi.service.KpiService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kpi")
public class KpiController {
    private final KpiService kpiServiceImpl;

    private Logger logger = LoggerFactory.getLogger(KpiController.class);

    @Autowired
    public KpiController(KpiService kpiServiceImpl) {
        this.kpiServiceImpl = kpiServiceImpl;
    }

    @RequiresPermissions("kpi:upload-export")
    @PostMapping(value = "/upload-export")
    public void uploadKpiModule(HttpServletRequest request, HttpServletResponse response) {
        // 获取上传文件
        MultipartResolver resolver = new CommonsMultipartResolver(request.getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile file = fileMap.get("file");

        // 获取月份
        String kpiDate = multipartRequest.getParameter("kpiDate");

        //校验文件
        KpiUtil.uploadFileCheck(file,kpiDate);

        //获取上传、导出文件名
        String uploadFileName = file.getOriginalFilename();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
        String kpiMonth = formater.format(new Date(Long.valueOf(kpiDate)));
        String exportFileName = kpiMonth + "月地府绩效明细.xlsx";

        //service层处理
        try {
            XSSFWorkbook excel = kpiServiceImpl.uploadAndExportKpi(uploadFileName, file.getBytes(), exportFileName,Long.valueOf(kpiDate));
            if(excel == null)
                throw new ParamsInvalidException(7004,"绩效报表生成失败");
            ExportControllerUtil.exportExcelToResponse(response, exportFileName, excel);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("导出绩效报表失败；message：" + e.getMessage());
            throw new ParamsInvalidException(7004,"绩效报表生成失败");
        }
    }

    @RequiresPermissions("kpi:history")
    @GetMapping("/history")
    public List<KpiHistoryDto> exportHistory(){
        return kpiServiceImpl.exportHistory();
    }

    @RequiresPermissions("kpi:export-history")
    @GetMapping("/export-history")
    public void exportHistoryFile(ExportHistoryParamDto paramDto,HttpServletResponse response){
        // 校验参数
        KpiUtil.exportHistoryParamCheck(paramDto);
        // 获取文件信息
        ExportHistoryResultDto resultDto = kpiServiceImpl.exportHistoryFile(paramDto);
        // 导出
        try {
            InputStream inputStream = new FileInputStream(resultDto.getSourceFile());
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            ExportControllerUtil.exportExcelToResponse(response, resultDto.getFileName(), wb);

        }catch (Exception e){
            e.printStackTrace();
            logger.warn("导出历史绩效报表失败 ： " + e);
            throw new ParamsInvalidException(7005,"导出历史绩效报表失败");
        }
    }

    @RequiresPermissions("kpi:export-again")
    @GetMapping("/export-again")
    public void exportAgain(Long id,HttpServletResponse response){
        ExportAgainDto dto = kpiServiceImpl.exportAgain(id);
        if(dto == null || dto.getFileName() == null || dto.getWb() == null){
            logger.warn("重新生成绩效报表失败");
            throw new ParamsInvalidException(7006,"重新生成绩效报表失败");
        }

        try {
            ExportControllerUtil.exportExcelToResponse(response, dto.getFileName(), dto.getWb());
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("重新生成绩效报表失败 ： " + e);
            throw new ParamsInvalidException(7006,"重新生成绩效报表失败");
        }
    }
}
