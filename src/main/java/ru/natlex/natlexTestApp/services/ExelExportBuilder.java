package ru.natlex.natlexTestApp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;

import java.util.List;
import java.util.Map;


public class ExelExportBuilder extends AbstractXlsView {

    private GeologicalClassRepository geologicalClassRepository;
    private int columnCount;
    public ExelExportBuilder(GeologicalClassRepository geologicalClassRepository, int columnCount) {
        this.geologicalClassRepository = geologicalClassRepository;
        this.columnCount = columnCount;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment;fileName=exportSections.xls");


        @SuppressWarnings("unchecked")
        List<Section> sections = (List<Section>) model.get("sections");
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
}
