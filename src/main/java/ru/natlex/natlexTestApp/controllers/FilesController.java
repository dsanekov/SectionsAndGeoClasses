package ru.natlex.natlexTestApp.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.dto.UnsuccessfulResponse;
import ru.natlex.natlexTestApp.services.JobService;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.io.IOException;
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
    public ResponseEntity<Object> importFile(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            return new ResponseEntity<>(new UnsuccessfulResponse("File is empty!",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
        if(!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')).equals(".xls")){
            return new ResponseEntity<>(new UnsuccessfulResponse("The file extension must be .xls!",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
        int jobId = jobService.startImport(file);
        return new ResponseEntity<>(jobId,HttpStatus.OK);
    }

    @GetMapping("/import/{id}")
    public ResponseEntity<Object> importFile(@PathVariable("id") int id) {
        if(Job.findJobById(id) == null){
            return new ResponseEntity<>(new UnsuccessfulResponse("Job with this id does not exist!",HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }
        if(Job.findJobById(id).getExelExportBuilder() != null){
            return new ResponseEntity<>(new UnsuccessfulResponse("This job is export job!",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
        JobStatus jobStatus = jobService.getJobStatus(id);
        return new ResponseEntity<>(jobStatus,HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<Object> exportFile() {
        int jobId = jobService.startExport();
        return new ResponseEntity<>(jobId,HttpStatus.OK);
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<Object> resultOfParsing(@PathVariable("id") int id) {
        if(Job.findJobById(id) == null){
            return new ResponseEntity<>(new UnsuccessfulResponse("Job with this id does not exist!", HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }
        if(Job.findJobById(id).getExelExportBuilder() == null){
            return new ResponseEntity<>(new UnsuccessfulResponse("This job is import job!", HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
        JobStatus jobStatus = jobService.getJobStatus(id);
        return new ResponseEntity<>(jobStatus,HttpStatus.OK);
    }

    @GetMapping("/export/{id}/file")
    public void returnsFileByJobId(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        if(jobService.getJobStatus(id).equals(JobStatus.IN_PROGRESS)){
            response.sendError(HttpStatus.BAD_REQUEST.value(),"Exporting is in process!)");
            return;
        }
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=sections_" + currentDateTime + ".xls";
        response.setHeader(headerKey, headerValue);
        jobService.returnsFileByJobId(id,response);
    }
}
