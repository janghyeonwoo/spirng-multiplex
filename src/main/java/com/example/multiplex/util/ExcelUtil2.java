package com.example.multiplex.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


public class ExcelUtil2 {

    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;
    private final int startRowNum;
    private final int endColumnNum;
    private final OPCPackage opcPackage;

    public ExcelUtil2(MultipartFile file, final int startRowNum, final int endColumnNum) {
        try {
            this.startRowNum = startRowNum;
            this.endColumnNum = endColumnNum;
            this.opcPackage = OPCPackage.open(file.getInputStream());
            this.xssfWorkbook = new XSSFWorkbook(this.opcPackage);
            this.xssfSheet = xssfWorkbook.getSheetAt(0);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public ExcelUtil2 init() {
        try {
            this.xssfWorkbook.close();
            xssfWorkbook = new XSSFWorkbook(opcPackage);
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } catch (Exception e){
            throw new RuntimeException();
        }
        return this;
    }


    public List<Map<String,Object>> getExcelData(){
        List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();
        for (int rowNum = startRowNum; rowNum < xssfSheet.getLastRowNum() + 1; rowNum ++){
            XSSFRow row = this.xssfSheet.getRow(rowNum);
            if(row.getCell(row.getFirstCellNum()) != null && !row.getCell(0).toString().isEmpty()){
                Map<String,Object> cellMap = new HashMap<>();
                for(int curColumnNum = 0; curColumnNum <= this.endColumnNum; curColumnNum++){
                    XSSFCell cell = row.getCell(curColumnNum);
                    cellMap.put(String.valueOf(curColumnNum), getCellValue(cell));
                }
                excelList.add(cellMap);
            }
        }
        return excelList;
    }
//    public List<Map<String,Object>> getExcelData2(){
//        List<Map<String,Object>> excelList = new ArrayList<>();
//        for (Row row : this.xssfSheet) {
//            Iterator<Cell> cellIterator = row.cellIterator();
//            Map<String, Object> cellMap = new HashMap<>();
//            while (cellIterator.hasNext()) {
//                XSSFCell cell = (XSSFCell) cellIterator.next();
//                cellMap.put(String.valueOf(cell.getColumnIndex()), getCellValue(cell));
//            }
//            excelList.add(cellMap);
//        }
//        return excelList;
//    }

//    public List<Map<String,Object>> getExcelData2(){
//
//        Iterator<Row> rowIterator = this.xssfSheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.cellIterator();
//            while(cellIterator.hasNext()){
//                Cell cell = cellIterator.next();
//                getCellValue(cell);
//            }
//
//        }
//
//    }

     public String getCellValue(XSSFCell cell) {

        String value = "";

        if (cell == null) {
            return value;
        }

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = (int) cell.getNumericCellValue() + "";
                break;
            default:
                break;
        }
        return value;
    }
}
