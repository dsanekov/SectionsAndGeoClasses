package ru.natlex.natlexTestApp.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.services.JobService;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FilesController {
    private final JobService jobService;

    @Autowired
    public FilesController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/import")
    public int importFile(@RequestParam("file") MultipartFile file) {
        return jobService.startImport(file);
    }

    @GetMapping("/import/{id}")
    public JobStatus importFile(@PathVariable("id") int id) {
        return jobService.getJobStatus(id);
    }

    @GetMapping("/export")
    public int exportFile() {
        return jobService.startExport();
    }

    @GetMapping("/export/{id}")
    public JobStatus resultOfParsing(@PathVariable("id") int id) {
        return jobService.getJobStatus(id);
    }

    @GetMapping("/export/{id}/file")
    public void returnsFileByJobId(@PathVariable("id") int id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=sections_" + currentDateTime + ".xls";
        response.setHeader(headerKey, headerValue);
        jobService.returnsFileByJobId(id,response);
    }
}
