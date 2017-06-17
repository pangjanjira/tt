/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.scb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author s60974
 */
public class ExcelUtils {

    public static String XLS = "xls";
    public static String XLSX = "xlsx";

    public Workbook getWorkbook(String fileName, String extension) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        try {
            FileInputStream fileInput = new FileInputStream(fileName);
            if (XLS.equalsIgnoreCase(extension)) {
                workbook = new HSSFWorkbook(fileInput);
            } else if (XLSX.equalsIgnoreCase(extension)) {
                workbook = new XSSFWorkbook(fileInput);
            }
        } catch (FileNotFoundException fne) {
            throw new Exception("FNE: " + fne.getMessage());
        } catch (IOException ioe) {
            throw new Exception("IOE: " + ioe.getMessage());
        } catch (Exception e) {
            throw new Exception("Error in getWorkbook: " + e.getMessage());
        }

        return workbook;
    }

    public Workbook getWorkbook(String extension) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        try {
            if (XLS.equalsIgnoreCase(extension)) {
                workbook = new HSSFWorkbook();
            } else if (XLSX.equalsIgnoreCase(extension)) {
                workbook = new XSSFWorkbook();
            }
        } catch (Exception e) {
            throw new Exception("Error in getWorkbook: " + e.getMessage());
        }

        return workbook;
    }

    public XSSFSheet initSheetXLSX(Workbook workbook, String sheetName) throws Exception {
        try {
            XSSFWorkbook book = (XSSFWorkbook) workbook;
            XSSFSheet output = book.createSheet(sheetName);
            return output;
        } catch (Exception e) {
            throw new Exception("Error in getSheetXLSX: " + e.getMessage());
        }
    }

    public HSSFSheet initSheetXLS(Workbook workbook, String sheetName) throws Exception {
        try {
            HSSFWorkbook book = (HSSFWorkbook) workbook;
            HSSFSheet output = book.createSheet(sheetName);
            return output;
        } catch (Exception e) {
            throw new Exception("Error in getSheetXLS: " + e.getMessage());
        }
    }

    public boolean generateExcelFile(String fileName, String extension, Map<String, Map> dataSheets, Map<String, String> dataFormats) throws Exception {
        boolean output = false;
        try {
            Workbook workbook = getWorkbook(extension);
            Object sheet = new Object();
            CellStyle decimalCS = null;
            CellStyle dateCS = workbook.createCellStyle();
            Set<String> sheetNames = dataSheets.keySet();

            for (String sheetName : sheetNames) {
                if (XLS.equalsIgnoreCase(extension)) {
                    sheet = initSheetXLS(workbook, sheetName);
                } else if (XLSX.equalsIgnoreCase(extension)) {
                    sheet = initSheetXLSX(workbook, sheetName);
                }

                Map<String, Object[]> data = dataSheets.get(sheetName);

                //Map<String, Object[]> data = new TreeMap<String, Object[]>(unSortedData);
                //Iterate over data and write to sheet
                Set<String> keyset = data.keySet();

                int rownum = 0;
                for (String key : keyset) {
                    Row row = null;
                    if (XLS.equalsIgnoreCase(extension)) {
                        row = ((HSSFSheet) sheet).createRow(rownum++);
                    } else if (XLSX.equalsIgnoreCase(extension)) {
                        row = ((XSSFSheet) sheet).createRow(rownum++);
                    }
                    Object[] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        String dataFormat = dataFormats.get(String.valueOf(cellnum));
                        String type = dataFormat.split("\\;")[0];
                        String format = "";
                        if ("BigDecimal".equalsIgnoreCase(type) || "Date".equalsIgnoreCase(type)) {
                            format = dataFormat.split("\\;")[1];
                        }
                        if (XLS.equalsIgnoreCase(extension)) {
                            HSSFDataFormat df = ((HSSFWorkbook) workbook).createDataFormat();
                            if ("BigDecimal".equalsIgnoreCase(type)) {
                                decimalCS = workbook.createCellStyle();
                                decimalCS.setDataFormat(((HSSFWorkbook) workbook).getCreationHelper().createDataFormat().getFormat(format));
                            }
                            if ("Date".equalsIgnoreCase(type)) {
                                dateCS.setDataFormat(df.getFormat(format));
                            }
                        } else if (XLSX.equalsIgnoreCase(extension)) {
                            XSSFDataFormat df = ((XSSFWorkbook) workbook).createDataFormat();
                            if ("Date".equalsIgnoreCase(type)) {
                                dateCS.setDataFormat(df.getFormat(format));
                            }
                        }
                        if (obj != null) {
                            if (obj instanceof String) {
                                cell.setCellValue((String) obj);
                            } else if (obj instanceof Integer) {
                                cell.setCellValue((Integer) obj);
                            } else if (obj instanceof BigDecimal) {
                                BigDecimal val = new BigDecimal(String.valueOf(obj));
                                cell.setCellValue(val.doubleValue());
                                cell.setCellStyle(decimalCS);
                            } else if (obj instanceof Date) {
                                cell.setCellValue((Date) obj);
                                cell.setCellStyle(dateCS);
                            } else {
                                cell.setCellValue(String.valueOf(obj));
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }

            try {
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(new File(fileName));
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            output = true;
        } catch (Exception e) {
            throw new Exception("Error in generateExcelFile: " + e.getMessage());
        }

        return output;
    }
}
