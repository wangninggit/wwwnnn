package com.viathink.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportExcelUtil {
    public static final String EXCEL_NAME_QUERY_REGION_CONTRAST = "queryRegionContrast.xlsx";
    public static final String EXCEL_NAME_QUERY_PROVINCE_CONTRAST = "queryProvinceContrast.xlsx";
    public static final String EXCEL_NAME_QUERY_TESTING_ITEM_CONTRAST = "queryTestingItemContrast.xlsx";
    public static final String EXCEL_NAME_REPORT_BUSINESS_DAILY_DETAIL = "reportsBusinessDailyDetail.xlsx";
    public static final String EXCEL_NAME_REPORT_BUSINESS_MONTH_DETAIL = "reportsBusinessMonthDetail.xlsx";
    public static final String EXCEL_NAME_REPORT_BUSINESS_YEAR_DETAIL = "reportsBusinessYearDetail.xlsx";
    public static final String EXCEL_NAME_QUERY_DAILY_COUNT = "queryDailyCount.xlsx";
    public static final String EXCEL_NAME_QUERY_BUSINESS_DETAIL = "queryBusinessDetail.xlsx";
    public static final String EXCEL_NAME_REPORTS_REGION_CONTRAST = "reportsRegionContrast.xlsx";
    public static final String EXCEL_NAME_REPORTS_PROFITS = "reportsProfits.xlsx";
    public static final String EXCEL_NAME_REPORTS_PROVINCE_CONTRAST = "reportsProvinceContrast.xlsx";
    public static final String EXCEL_NAME_REPORTS_TESTING_ITEM_CONTRAST = "reportsTestingItemContrast.xlsx";
    public static final String EXCEL_NAME_QUERY_ORDER_WITHOUT_INVOICE = "queryOrderWithoutInvoice.xlsx";
    public static final String EXCEL_NAME_QUERY_ORDER_TREND_MONTH_AVG = "queryOrderTrendMonthAvg.xlsx";

    public static XSSFWorkbook readExcel(JSONObject jsonObject,String fileName) throws IOException,ParseException {
        String filePath = "excel/"+fileName;
        //读取Excel模板文件
        InputStream fileInputStream =
                ExportExcelUtil.class.getClassLoader().getResourceAsStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        //获取属性、属性类型、单元格cellStyle
        XSSFSheet sheetExample = wb.getSheetAt(1);
        XSSFRow keyRow = sheetExample.getRow(0);
        XSSFRow typeRow = sheetExample.getRow(1);
        XSSFRow  cellStyleRow = sheetExample.getRow(2);
        XSSFRow start = sheetExample.getRow(3);
        int startRow = (int) start.getCell(0).getNumericCellValue();
        //属性数组
        List<String> keys = new ArrayList<>();
        //type 类型数组
        List<String> cellTypes = new ArrayList<>();
        //cellStyle 数组
        List<CellStyle> cellStyleList = new ArrayList<>();

        for (Cell cell:keyRow) {
            keys.add(cell.getStringCellValue());
        }
        for (Cell cell:typeRow) {
            cellTypes.add(cell.getStringCellValue());
        }
        for (Cell cell:cellStyleRow) {
            cellStyleList.add(cell.getCellStyle());
        }
        //数据表
        XSSFSheet sheet = wb.getSheetAt(0);
        //遍历List插入数据
        List list = jsonObject.getJSONArray("list");
        for(int i=0;i<list.size();i++){
            //获取该行数据
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(list.get(i)));
            //创建行，设置起始行
            XSSFRow row = sheet.createRow(i+startRow-1);
            //创建列
            for(int j=0;j<keys.size();j++){
                //创建列
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyleList.get(j));
                if(j==0){
                    //第一列，序号列
                    cell.setCellValue(i+1);
                }else{
                    setCellValue(cell,j,object,cellTypes,keys);
                }
            }
        }

        //总计sum
        JSONObject sum = jsonObject.getJSONObject("sum");
        if(sum!=null){
            XSSFRow row = sheet.createRow(list.size()+startRow-1);
            //创建一个单元格

            for(int j=2;j<keys.size();j++){
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyleList.get(j));
                setCellValue(cell,j,sum,cellTypes,keys);
            }

            Cell cellSum = row.createCell(0);
            cellSum.setCellValue("总计");
            //合并单元格（起始行,结束行,起始列,结束列）
            sheet.addMergedRegion(new CellRangeAddress(list.size()+startRow-1,list.size()+startRow-1,0,1));

        }
        wb.removeSheetAt(1);
        //写入在输出流
        return wb;
    }

    public static XSSFWorkbook generateExcel(JSONObject jsonObject, String fileName) throws IOException, ParseException {
        String filePath = "excel/" + fileName;
        //读取Excel模板文件
        InputStream fileInputStream =
                ExportExcelUtil.class.getClassLoader().getResourceAsStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        //获取属性、属性类型、单元格cellStyle
        XSSFSheet sheetExample = wb.getSheetAt(1);
        XSSFRow keyRow = sheetExample.getRow(0);
        XSSFRow typeRow = sheetExample.getRow(1);
        XSSFRow cellStyleRow = sheetExample.getRow(2);
        XSSFRow start = sheetExample.getRow(3);
        int startRow = (int) start.getCell(0).getNumericCellValue();
        //属性数组
        List<String> keys = new ArrayList<>();
        //type 类型数组
        List<String> cellTypes = new ArrayList<>();
        //cellStyle 数组
        List<CellStyle> cellStyleList = new ArrayList<>();

        for (Cell cell : keyRow) {
            keys.add(cell.getStringCellValue());
        }
        for (Cell cell : typeRow) {
            cellTypes.add(cell.getStringCellValue());
        }
        for (Cell cell : cellStyleRow) {
            cellStyleList.add(cell.getCellStyle());
        }
        //数据表
        XSSFSheet sheet = wb.getSheetAt(0);
        //遍历List插入数据
        int nextRow = insertRowData(jsonObject.getJSONArray("list"), sheet, startRow, keys, cellTypes, cellStyleList);

        //总计sum
        JSONObject sum = jsonObject.getJSONObject("sum");
        if (sum != null) {
            XSSFRow row = sheet.createRow(nextRow - 1);
            //创建一个单元格

            for (int j = 2; j < keys.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyleList.get(j));
                setCellValue(cell, j, sum, cellTypes, keys);
            }

            Cell cellSum = row.createCell(0);
            cellSum.setCellValue("总计");
            //合并单元格（起始行,结束行,起始列,结束列）
            sheet.addMergedRegion(new CellRangeAddress(nextRow - 1, nextRow - 1, 0, 1));

        }
        wb.removeSheetAt(1);
        //写入在输出流
        return wb;
    }

    private static int insertRowData(JSONArray jsonArray, XSSFSheet sheet, int startRow, List<String> keys, List<String> cellTypes, List<CellStyle> cellStyleList) throws ParseException {
        for (int i = 0; i < jsonArray.size(); i++) {
            //获取该行数据
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //创建行，设置起始行
            XSSFRow row;
            if ((row = sheet.getRow(startRow - 1)) == null) {
                row = sheet.createRow(startRow - 1);
            }
            startRow += 1;
            //创建列
            int newStartRow = startRow;
            for (String jsonKey : jsonObject.keySet()) {
                if (jsonObject.getString(jsonKey) != null && jsonObject.getString(jsonKey).charAt(0) == '[') {
                    int tempRow = insertRowData(jsonObject.getJSONArray(jsonKey), sheet, startRow - 1, keys, cellTypes, cellStyleList);
                    newStartRow = (newStartRow > tempRow) ? newStartRow : tempRow;
                }
            }
            for (String jsonKey : jsonObject.keySet()) {
                if (jsonObject.getString(jsonKey) != null && jsonObject.getString(jsonKey).charAt(0) != '[') {
                    if (keys.contains(jsonKey)) {
                        int index = keys.indexOf(jsonKey);
                        Cell cell = row.createCell(index);
                        cell.setCellStyle(cellStyleList.get(index));
                        setCellValue(cell, index, jsonObject, cellTypes, keys);
                        if (newStartRow > startRow)
                            sheet.addMergedRegion(new CellRangeAddress(startRow - 2, newStartRow - 2, index, index));
                    }
                }
            }
            int index = keys.indexOf("number");
            Cell cell = row.createCell(index);
            cell.setCellStyle(cellStyleList.get(index));
            cell.setCellValue(i + 1);
            if (newStartRow > startRow)
                sheet.addMergedRegion(new CellRangeAddress(startRow - 2, newStartRow - 2, index, index));
            startRow = newStartRow;
        }
        return startRow;
    }

    private static void setCellValue(Cell cell, int j, JSONObject object, List<String> cellTypes, List<String> keys) throws ParseException{
        String dateStr = object.getString(keys.get(j));
        switch (cellTypes.get(j)) {
            case "number"://数值
                if(object.containsKey(keys.get(j))){
                    cell.setCellValue(object.getIntValue(keys.get(j)));
                }else{
                    cell.setCellValue("");
                }
                break;
            case "percentage"://数值
                if(object.containsKey(keys.get(j))){
                    cell.setCellValue(object.getIntValue(keys.get(j))/100.0);
                }else{
                    cell.setCellValue("");
                }
                break;
            case "amount"://金额
                if(object.containsKey(keys.get(j))){
                    Double money = (double) object.getLongValue(keys.get(j)) /1000;
                    String f = String.valueOf(money);
                    BigDecimal b = new BigDecimal(f);
                    double f1 = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
                    cell.setCellValue(f1);
                }else{
                    cell.setCellValue("");
                }
                break;
            case "string"://字符串
                if(object.containsKey(keys.get(j))&& object.getString(keys.get(j))!=null){
                    cell.setCellValue(object.getString(keys.get(j)));
                }else{
                    cell.setCellValue("");
                }
                break;
            case "dateDay"://日
                if(object.containsKey(keys.get(j))&& dateStr!=null){
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
                }else{
                    cell.setCellValue("");
                }
                break;
            case "dateMonth"://月
                if(object.containsKey(keys.get(j))&& dateStr!=null){
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM").parse(dateStr));
                }else{
                    cell.setCellValue("");
                }
                break;
            case "dateYear"://年
                if(object.containsKey(keys.get(j))&& dateStr!=null){
                    cell.setCellValue(new SimpleDateFormat("yyyy").parse(dateStr));
                }else{
                    cell.setCellValue("");
                }
                break;
            case "dateLong"://Long类型的日期
                Long date = object.getLongValue(keys.get(j));
                if(object.containsKey(keys.get(j))){
                    cell.setCellValue(new Date(date));
                }else{
                    cell.setCellValue("");
                }
                break;
            default:
        }
    }

    public static void incomeExcel(JSONObject jsonObject, String fileName) throws IOException,ParseException {
        //读取Excel模板文件
        String filePath = "excel/"+fileName;
        //读取Excel模板文件
        InputStream fileInputStream =
                ExportExcelUtil.class.getClassLoader().getResourceAsStream(filePath);

        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);

        //获取属性、属性类型、单元格cellStyle
        XSSFSheet sheetExample = wb.getSheetAt(1);
        XSSFRow keyRow = sheetExample.getRow(0);
        XSSFRow typeRow = sheetExample.getRow(1);
        XSSFRow  cellStyleRow = sheetExample.getRow(2);
        XSSFRow start = sheetExample.getRow(3);
        int startRow = (int) start.getCell(0).getNumericCellValue();
        int invoiceStartCell = (int) start.getCell(1).getNumericCellValue();
        int testStartCell = (int) start.getCell(2).getNumericCellValue();
        int testEndCell = (int) start.getCell(3).getNumericCellValue();

        //属性数组
        List<String> keys = new ArrayList<>();
        //type 类型数组
        List<String> cellTypes = new ArrayList<>();
        //cellStyle 数组
        List<CellStyle> cellStyleList = new ArrayList<>();


        for (Cell cell:keyRow) {
            keys.add(cell.getStringCellValue());
        }
        for (Cell cell:typeRow) {
            cellTypes.add(cell.getStringCellValue());
        }
        for (Cell cell:cellStyleRow) {
            cellStyleList.add(cell.getCellStyle());
        }
        int nowRow = startRow - 1;
        //数据表
        XSSFSheet sheet = wb.getSheetAt(0);

        //遍历List插入数据
        List list = jsonObject.getJSONArray("list");
        for(int i=0;i<list.size();i++){
            JSONObject jsonObject1 = JSONObject.parseObject(JSON.toJSONString(list.get(i)));
            //获取发票
            JSONArray invoices = jsonObject1.getJSONArray("invoices");
            //获取检测套餐
            JSONArray testings = jsonObject1.getJSONArray("testings");
            //该订单总共占多少行
            int rowSize;

            if(invoices.size() > testings.size()){
                //该订单总共的行数
                rowSize = invoices.size();  //5

                //合并的行数
                int mergeRow = invoices.size()/testings.size();  //2
                //最后一行多的行数
                int lastRow = invoices.size()% testings.size(); //1

                //发票
                for(int j=0;j<invoices.size();j++){
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(invoices.get(j)));
                    //发票每一行数据
                    XSSFRow row = sheet.createRow(nowRow+j);  //2、3、4、5、6
                    //发票的每一列
                    for(int z=invoiceStartCell;z<=testStartCell-1;z++){
                        Cell cell1 = row.createCell(z);
                        cell1.setCellStyle(cellStyleList.get(z));
                        setCellValue(cell1,z,object,cellTypes,keys);
                    }
                }
                //检测套餐
                for(int x=0;x<testings.size();x++) {
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(testings.get(x)));
                    //检测套餐的每一行数据,起始行nowRow+mergeRow*x ,结束行nowRow+mergeRow*x+mergeRow-1
                    XSSFRow rowTest = sheet.getRow(nowRow + mergeRow * x); //2、4
                    for (int y = testStartCell; y <= testEndCell; y++) {
                        Cell cell1 = rowTest.createCell(y);
                        cell1.setCellStyle(cellStyleList.get(y));
                        //合并单元格（起始行,结束行,起始列,结束列）
                        if(x==testings.size()-1){
                            //最后一行，合并多的行数
                            sheet.addMergedRegion(new CellRangeAddress(nowRow + mergeRow * x, nowRow + mergeRow * x + mergeRow - 1+lastRow, y, y));
                        }else if(mergeRow>1){
                            sheet.addMergedRegion(new CellRangeAddress(nowRow + mergeRow * x, nowRow + mergeRow * x + mergeRow - 1, y, y));
                        }
                        setCellValue(cell1,y,object,cellTypes,keys);
                    }

                }

            }else if(invoices.size()< testings.size()){
                rowSize = testings.size();  //5
                //invoices 3 testings 5
                int mergeRow = testings.size()/invoices.size(); //2  1
                int lastRow = testings.size() % invoices.size(); //1  2

                //检测套餐
                for(int j=0;j<testings.size();j++){
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(testings.get(j)));
                    //检测套餐每一行数据
                    XSSFRow row = sheet.createRow(nowRow+j);  //2、3、4、5、6
                    //检测套餐的每一列
                    for(int z=testStartCell;z<=testEndCell;z++){
                        Cell cell1 = row.createCell(z);
                        cell1.setCellStyle(cellStyleList.get(z));
                        setCellValue(cell1,z,object,cellTypes,keys);
                    }
                }

                //发票
                for(int x=0;x<invoices.size();x++) {
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(invoices.get(x)));
                    //发票的每一行数据,起始行nowRow+mergeRow*x ,结束行nowRow+mergeRow*x+mergeRow-1
                    XSSFRow rowTest = sheet.getRow(nowRow + mergeRow * x); //2、4
                    for (int y = invoiceStartCell; y <= testStartCell-1; y++) {
                        Cell cell1 = rowTest.createCell(y);
                        cell1.setCellStyle(cellStyleList.get(y));
                        //合并单元格（起始行,结束行,起始列,结束列）
                        if(x==invoices.size()-1){
                            //最后一行，合并多的行数
                            sheet.addMergedRegion(new CellRangeAddress(nowRow + mergeRow * x, nowRow + mergeRow * x + mergeRow - 1+lastRow, y, y));
                        }else if(mergeRow>1){
                            sheet.addMergedRegion(new CellRangeAddress(nowRow + mergeRow * x, nowRow + mergeRow * x + mergeRow - 1, y, y));
                        }
                        setCellValue(cell1,y,object,cellTypes,keys);
                    }

                }
            }else{
                rowSize = testings.size();
                //检测套餐
                for(int j=0;j<testings.size();j++){
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(testings.get(j)));
                    //检测套餐每一行数据
                    XSSFRow row = sheet.createRow(nowRow+j);  //2、3、4、5、6
                    //发票的每一列
                    for(int z=testStartCell;z<=testEndCell;z++){
                        Cell cell1 = row.createCell(z);
                        cell1.setCellStyle(cellStyleList.get(z));
                        setCellValue(cell1,z,object,cellTypes,keys);
                    }
                }

                //发票
                for(int j=0;j<invoices.size();j++){
                    JSONObject object = JSONObject.parseObject(JSON.toJSONString(invoices.get(j)));
                    //发票每一行数据
                    XSSFRow row = sheet.getRow(nowRow+j);  //2、3、4、5、6
                    //发票的每一列
                    for(int z=invoiceStartCell;z<=testStartCell-1;z++){
                        Cell cell1 = row.createCell(z);
                        cell1.setCellStyle(cellStyleList.get(z));
                        setCellValue(cell1,z,object,cellTypes,keys);
                    }
                }
            }

            //设置序号列
            XSSFRow rowOrder = sheet.getRow(nowRow);
            Cell cell = rowOrder.createCell(0);
            if(rowSize>1){
                //rowSize 等于1时不需要合并单元格
                sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,0,0));
            }
            cell.setCellValue(i+1);

            //订单信息
            for(int j=1;j<=invoiceStartCell-1;j++){
                Cell cell1 = rowOrder.createCell(j);
                cell1.setCellStyle(cellStyleList.get(j));
                //合并单元格（起始行,结束行,起始列,结束列）
                if(rowSize>1){
                    sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,j,j));
                }
                setCellValue(cell1,j,jsonObject1,cellTypes,keys);
            }
            nowRow = nowRow + rowSize;

        }

        //总计sum
        JSONObject sum = jsonObject.getJSONObject("sum");
        if(sum!=null){
            XSSFRow row = sheet.createRow(nowRow);
            //创建一个单元格

            for(int j=2;j<keys.size();j++){
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyleList.get(j));
                setCellValue(cell,j,sum,cellTypes,keys);
            }

            Cell cellSum = row.createCell(0);
            cellSum.setCellValue("总计");
            //合并单元格（起始行,结束行,起始列,结束列）
            sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow,0,1));

        }

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/lrq/Desktop/income2.xlsx");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();

    }

}
