package ru.natlex.natlexTestApp.services;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

@Service
public class XMLExportService {
    private int EXPORT_FILE_COUNTER = 0;
    private SectionsRepository sectionsRepository;
    private GeologicalClassRepository geologicalClassRepository;

    @Autowired
    public XMLExportService(SectionsRepository sectionsRepository, GeologicalClassRepository geologicalClassRepository) {
        this.sectionsRepository = sectionsRepository;
        this.geologicalClassRepository = geologicalClassRepository;
    }

    public int startExporting(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Job job = new Job(JobStatus.IN_PROGRESS);
        EXPORT_FILE_COUNTER++;

        executorService.execute(() -> {
            try {
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("Export");
                List<Section> sections = sectionsRepository.findAll();
                Row rowHead = sheet.createRow(0);
                Cell cellHead0 = rowHead.createCell(0);
                cellHead0.setCellValue("Section name");
                int columnCount = geologicalClassRepository.findMaxGeo()*2;
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
                String fileName = "exportSectionsFile" + EXPORT_FILE_COUNTER + ".xls";
                FileOutputStream out = new FileOutputStream(fileName);
                workbook.write(out);
                out.close();
                job.setJobStatus(JobStatus.DONE);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();

        return job.getId();
    }
}
