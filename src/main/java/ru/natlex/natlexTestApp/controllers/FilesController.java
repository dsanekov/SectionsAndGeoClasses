package ru.natlex.natlexTestApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.services.JobService;
import ru.natlex.natlexTestApp.util.JobStatus;

@RestController
public class FilesController {
    private final JobService jobService;

    @Autowired
    public FilesController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/import")
    public int importFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getName());
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
    public ModelAndView returnsFileByJobId(@PathVariable("id") int id) {

        return jobService.returnsFileByJobId(id);

    }
}
