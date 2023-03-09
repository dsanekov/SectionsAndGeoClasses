package ru.natlex.natlexTestApp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.natlex.natlexTestApp.util.JobStatus;

public interface JobService {
    int startImport(MultipartFile file);
    int startExport();
    JobStatus getJobStatus (int id);
    ResponseEntity<String> returnsFileByJobId(int id);
}
