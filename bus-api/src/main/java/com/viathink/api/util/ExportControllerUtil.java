package com.viathink.api.util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class ExportControllerUtil {
    public static void exportExcelToResponse(HttpServletResponse response, String filename, XSSFWorkbook excel) throws IOException {
        filename = URLEncoder.encode(filename, "UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();
        excel.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }
}