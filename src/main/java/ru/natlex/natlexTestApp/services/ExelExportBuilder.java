package ru.natlex.natlexTestApp.services;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExelExportBuilder {

    private int columnCount;
    private HSSFWorkbook workbook;
    public ExelExportBuilder(int columnCount) {
        this.columnCount = columnCount;
        workbook = new HSSFWorkbook();
    }

    protected void buildExcelDocument(List<Section> sections) throws Exception {

        Sheet sheet = workbook.createSheet("Sections");
        Row rowHead = sheet.createRow(0);
        Cell cellHead0 = rowHead.createCell(0);
        cellHead0.setCellValue("Section name");

        int classNum = 1;
        for (int i = 1; i <= columnCount ; i++) {
            String className = "Class " + classNum + " name";
            String classCode = "Class " + classNum + " code";
            Cell cellHead = rowHead.createCell(i);
            if(i%2 != 0){
                cellHead.setCellValue(className);
                continue;
            }
            cellHead.setCellValue(classCode);
            classNum++;
        }
        int rowNum = 1;
        for(Section section : sections){
            Row row = sheet.createRow(rowNum++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(section.getName());
            int cellNum = 1;
            for(GeologicalClass geologicalClass : section.getGeologicalClasses()){
                Cell cellName = row.createCell(cellNum++);
                cellName.setCellValue(geologicalClass.getName());
                Cell cellCode = row.createCell(cellNum++);
                cellCode.setCellValue(geologicalClass.getCode());
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        //You can save file on disk.
        //saveXLSFileOnPC();
    }
    public void saveXLSFileOnPC(){
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("sections.xls"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
