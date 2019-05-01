package com.viathink.core.util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileUtil {

    public static String saveWorkbook(XSSFWorkbook wb,String filePath){
        File targetFile = new File(filePath);
        if(!(targetFile.exists() || targetFile.mkdirs())) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + ".xlsx";

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath+fileName);
            wb.write(out);
            out.flush();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(out != null) {
                try {
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
