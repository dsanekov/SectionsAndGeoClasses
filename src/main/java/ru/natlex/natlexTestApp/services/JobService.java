package ru.natlex.natlexTestApp.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.util.JobStatus;

public interface JobService {
    int startImport(MultipartFile file);
    int startExport();
    JobStatus getJobStatus (int id);
    void returnsFileByJobId(int id, HttpServletResponse response);
}
