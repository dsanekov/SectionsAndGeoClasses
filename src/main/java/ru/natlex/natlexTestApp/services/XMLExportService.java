package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.models.Section;
import ru.natlex.natlexTestApp.repositories.GeologicalClassRepository;
import ru.natlex.natlexTestApp.repositories.SectionsRepository;
import ru.natlex.natlexTestApp.util.Job;
import ru.natlex.natlexTestApp.util.JobStatus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class XMLExportService {
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
        job.setModelAndView(new ModelAndView());
        executorService.execute(() -> {
            try {
                int columnCount = geologicalClassRepository.findMaxGeo()*2;
                job.getModelAndView().setView(new ExelExportBuilder(geologicalClassRepository, columnCount));
                List<Section> sections = sectionsRepository.findAll();
                job.getModelAndView().addObject("sections",sections);
                job.setJobStatus(JobStatus.DONE);
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
