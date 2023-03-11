package ru.natlex.natlexTestApp.services;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.natlex.natlexTestApp.models.GeologicalClass;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

@Service
public class XLSImportService {
    private SectionsRepository sectionsRepository;
    private GeologicalClassRepository geologicalClassRepository;

    @Autowired
    public XLSImportService(SectionsRepository sectionsRepository, GeologicalClassRepository geologicalClassRepository) {
        this.sectionsRepository = sectionsRepository;
        this.geologicalClassRepository = geologicalClassRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int startImporting(MultipartFile multipartFile){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Job job = new Job(JobStatus.IN_PROGRESS);
        executorService.execute(() -> {
            try {
                InputStream stream = multipartFile.getInputStream();

                HSSFWorkbook workbook = new HSSFWorkbook(stream);
                HSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {//строки
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Section section = new Section();
                    int geoCurrentId = 0;

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if(cell.getRowIndex()==0){
                            break;
                        }

                        if(cell.getColumnIndex()==0 && !cell.getStringCellValue().isEmpty() && !cell.getStringCellValue().isBlank() && cell.getCellType() != NUMERIC){
                            //первый столбец, не пустой - имя секции. Создаем секцию.
                            String sectionsName = cell.getStringCellValue();
                            section.setName(sectionsName);
                            sectionsRepository.save(section);
                        }

                        GeologicalClass geologicalClass = new GeologicalClass();

                        if(cell.getColumnIndex() != 0 && cell.getColumnIndex() % 2 != 0 && !cell.getStringCellValue().isEmpty() && !cell.getStringCellValue().isBlank() && cell.getCellType() != NUMERIC){
                            //1,3,5,7 geoclass name
                            String geoClassName = cell.getStringCellValue();
                            geologicalClass.setName(geoClassName);
                            geologicalClass.setSection(section);
                            geologicalClassRepository.save(geologicalClass);
                            geoCurrentId = geologicalClass.getId();
                        }
                        if(cell.getColumnIndex() != 0 && cell.getColumnIndex() % 2 == 0 && !cell.getStringCellValue().isEmpty() && !cell.getStringCellValue().isBlank() && cell.getCellType() != NUMERIC){
                            //2,4,6,8 geoclass code
                            String geoClassCode = cell.getStringCellValue();
                            Optional<GeologicalClass> foundGeoOptional = geologicalClassRepository.findById(geoCurrentId);
                            if(foundGeoOptional.isPresent()){
                                GeologicalClass updateGeoClass = foundGeoOptional.get();
                                updateGeoClass.setCode(geoClassCode);
                                geologicalClassRepository.save(updateGeoClass);
                            }
                        }
                    }
                }
                job.setJobStatus(JobStatus.DONE);
                stream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                job.setJobStatus(JobStatus.ERROR);
            }
        });
        executorService.shutdown();
            return job.getId();
   }

}
