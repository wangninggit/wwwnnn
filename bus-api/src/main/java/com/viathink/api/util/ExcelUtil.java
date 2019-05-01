package com.viathink.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.viathink.core.util.ExportExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    public static final String EXCEL_NAME_INCOME_DETAIL = "incomeDetail.xlsx";
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
                    cell.setCellValue((double) object.getIntValue(keys.get(j)) /100);
                }else{
                    cell.setCellValue("");
                }
                break;
            case "amount"://金额
                if(object.containsKey(keys.get(j))){
                    Double money = (double) object.getLongValue(keys.get(j)) /1000;
                    if(money!=null){
                        String f = String.valueOf(money);
                        BigDecimal b = new BigDecimal(f);
                        double f1 = b.setScale(2,RoundingMode.HALF_UP).doubleValue();
                        cell.setCellValue(f1);
                    } else{
                        cell.setCellValue(0);
                    }
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

    public static void incomeExcel(JSONObject jsonObject, String fileName, HttpServletResponse response,String newFileName) throws IOException,ParseException {
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

        //缴款信息开始列，发票开始列，检测项目开始列和结束列
        int paymentStartCell = (int) start.getCell(1).getNumericCellValue();
        int invoiceStartCell = (int) start.getCell(2).getNumericCellValue();
        int testStartCell = (int) start.getCell(3).getNumericCellValue();
        int testEndCell = (int) start.getCell(4).getNumericCellValue();
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
        //起始行
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
            //获取缴款信息
            JSONArray payments = jsonObject1.getJSONArray("payments");
            List<Integer> rowSizes = Arrays.asList(invoices.size(), testings.size(), payments.size());
            //该订单总共占多少行
            int rowSize =  Collections.max(rowSizes);
            //创建该订单占据的所有行
            for(int j=0;j<rowSize;j++){
                sheet.createRow(nowRow+j);
            }

            arrayListSetCellValue(payments,nowRow,sheet,paymentStartCell,invoiceStartCell-3,cellStyleList,cellTypes,keys);
            arrayListSetCellValue(invoices,nowRow,sheet,invoiceStartCell,testStartCell-3,cellStyleList,cellTypes,keys);
            arrayListSetCellValue(testings,nowRow,sheet,testStartCell,testEndCell,cellStyleList,cellTypes,keys);
            //设置序号列
            XSSFRow rowOrder = sheet.getRow(nowRow);
            Cell cell = rowOrder.createCell(0);
            if(rowSize>1){
                //rowSize 等于1时不需要合并单元格
                sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,0,0));
            }
            cell.setCellValue(i+1);

            //订单信息
            for(int j=1;j<=paymentStartCell-1;j++){
                Cell cell1 = rowOrder.createCell(j);
                cell1.setCellStyle(cellStyleList.get(j));
                //合并单元格（起始行,结束行,起始列,结束列）
                if(rowSize>1){
                    sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,j,j));
                }
                setCellValue(cell1,j,jsonObject1,cellTypes,keys);
            }

            Cell invoiceTest = rowOrder.createCell(testStartCell-1);
            Cell payInvoice = rowOrder.createCell(invoiceStartCell-1);
            payInvoice.setCellStyle(cellStyleList.get(invoiceStartCell-1));
            invoiceTest.setCellStyle(cellStyleList.get(testStartCell-1));

            //合并单元格（起始行,结束行,起始列,结束列）
            if(rowSize>1){
                sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,invoiceStartCell-1,invoiceStartCell-1));
                sheet.addMergedRegion(new CellRangeAddress(nowRow,nowRow+rowSize-1,testStartCell-1,testStartCell-1));
            }
            setCellValue(payInvoice,invoiceStartCell-1,jsonObject1,cellTypes,keys);
            setCellValue(invoiceTest,testStartCell-1,jsonObject1,cellTypes,keys);
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
        wb.removeSheetAt(1);
        newFileName = URLEncoder.encode(newFileName,"UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName);
        OutputStream outPutStream = response.getOutputStream();
//        //定义一个输出流
//        FileOutputStream fileOutputStream = new FileOutputStream("income1.xlsx");
//        //写入在输出流
//        excel.write(fileOutputStream);
        wb.write(outPutStream);
        outPutStream.flush();
        outPutStream.close();
    }

    public static void arrayListSetCellValue(JSONArray payments,int nowRow,XSSFSheet sheet,int startCell,int endCell,List<CellStyle> cellStyleList,List<String> cellTypes,List<String> keys) throws ParseException {
        for(int p=0;p<payments.size();p++){
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(payments.get(p)));
            //缴款信息每一行数据
            XSSFRow row = sheet.getRow(nowRow+p);  //2、3、4、5、6
            //缴款信息的每一列
            for(int z=startCell;z<=endCell;z++){
                Cell cell1 = row.createCell(z);
                cell1.setCellStyle(cellStyleList.get(z));
                setCellValue(cell1,z,object,cellTypes,keys);
            }
        }
    }

}
