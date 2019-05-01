package com.viathink.api.util;

import com.viathink.api.common.exception.ParamsInvalidException;
import com.viathink.core.business.kpi.dto.ExportHistoryParamDto;
import com.viathink.core.business.kpi.util.KpiUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KpiUtil {

    public static void uploadFileCheck(MultipartFile file,String date){
        if(file == null || !file.getOriginalFilename().matches(".*.xlsx$"))
            throw new ParamsInvalidException(7001,"上传文件错误");

        try {
            if(date == null || Long.valueOf(date) < 1514736000000L || Long.valueOf(date) >= addAndGetMonthStart(new Date(),1)){
                throw new ParamsInvalidException(7002,"日期格式错误");
            }
        }catch (Exception e){
            throw new ParamsInvalidException(7002,"日期格式错误");
        }

        try {
            InputStream fileInputStream = file.getInputStream();
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheetExample = wb.getSheetAt(0);
            XSSFRow row = sheetExample.getRow(1);
            List<String> columnNames = new ArrayList<>();

            for(Cell cell:row){
                columnNames.add(cell.getStringCellValue());
            }
            row = sheetExample.getRow(2);
            columnNames.set(0,row.getCell(0).getStringCellValue());
            for(int i=5; i<= 12; i++){
                columnNames.set(i,row.getCell(i).getStringCellValue());
            }
            if(!(   columnNames.get(0).equals("工号") &&
                    columnNames.get(1).equals("地服") &&
                    columnNames.get(2).equals("大区") &&
                    columnNames.get(3).equals("省份") &&
                    columnNames.get(4).equals("地市") &&
                    columnNames.get(5).equals("G1") &&
                    columnNames.get(6).equals("G0") &&
                    columnNames.get(7).equals("△G") &&
                    columnNames.get(8).equals("上月△G") &&
                    columnNames.get(9).equals("累计△G") &&
                    columnNames.get(10).equals("系数a2") &&
                    columnNames.get(11).equals("系数b2") &&
                    columnNames.get(12).equals("a2*G1+b2*△G")
            )){
                throw new ParamsInvalidException(7003,"excel文件内容错误");
            }
        }catch (Exception e){
            throw new ParamsInvalidException(7003,"excel文件内容错误");
        }
    }

    public static Long addAndGetMonthStart(Date date, int monthDiff) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthDiff);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM");
        String format = formater.format(cal.getTime());
        return formater.parse(format).getTime();
    }

    public static void exportHistoryParamCheck(ExportHistoryParamDto paramDto){
        if(paramDto == null)
            throw new ParamsInvalidException(1006,"参数错误");
        if(paramDto.getType() == null || !(paramDto.getType().equals(KpiUtils.HISTORY_TYPE_UPLOAD) || (paramDto.getType().equals(KpiUtils.HISTORY_TYPE_EXPORT))))
            throw new ParamsInvalidException(1006,"参数错误");
        if(paramDto.getId() == null)
            throw new ParamsInvalidException(1006,"参数错误");
    }
}
