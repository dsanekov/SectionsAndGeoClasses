package ru.natlex.natlexTestApp.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.exceptions.ExportException;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.io.IOException;

@Service
public class JobServiceImp implements JobService{

    private SectionsRepository sectionsRepository;
    private GeologicalClassRepository geologicalClassRepository;
    private XLSImportService xlsImportService;
    private XMLExportService xmlExportService;

    @Autowired
    public JobServiceImp(SectionsRepository sectionsRepository, GeologicalClassRepository geologicalClassRepository, XLSImportService xlsImportService, XMLExportService xmlExportService) {
        this.sectionsRepository = sectionsRepository;
        this.geologicalClassRepository = geologicalClassRepository;
        this.xlsImportService = xlsImportService;
        this.xmlExportService = xmlExportService;
    }

    @Override
    public int startImport(MultipartFile file) {
        return xlsImportService.startImporting(file);
    }

    @Override
    public int startExport() {
        return xmlExportService.startExporting();
    }

    @Override
    public JobStatus getJobStatus(int id) {
       return Job.findJobById(id).getJobStatus();
    }

    @Override
    public void returnsFileByJobId(int id, HttpServletResponse response) {
        try {
            if(Job.findJobById(id).getJobStatus().equals(JobStatus.IN_PROGRESS)){
                throw new ExportException();
            }
            Job.findJobById(id).getExelExportBuilder().export(response);
        }
        catch (ExportException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
