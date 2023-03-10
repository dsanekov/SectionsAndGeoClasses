package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

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
    public ResponseEntity<String> returnsFileByJobId(int id) {
        return null;
    }
}
