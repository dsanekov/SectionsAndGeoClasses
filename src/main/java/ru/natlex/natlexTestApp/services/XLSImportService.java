package ru.natlex.natlexTestApp.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Iterator;

public class XLSImportService {
    public static void main(String[] args) {
        //public void startImporting(){
            try {
                FileInputStream file = new FileInputStream("abc.xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    // For each row, iterate through all the
                    // columns
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        System.out.println(cell);

                    }

                    System.out.println("");
                }

                file.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
   //}

}
