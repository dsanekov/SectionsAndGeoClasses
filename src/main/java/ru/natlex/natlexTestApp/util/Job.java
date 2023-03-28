package ru.natlex.natlexTestApp.util;

import org.springframework.web.servlet.ModelAndView;
import ru.natlex.natlexTestApp.services.ExelExportBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Job {
    private static AtomicInteger JOBS_COUNT = new AtomicInteger(0);
    private static CopyOnWriteArrayList<Job> jobs = new CopyOnWriteArrayList<>();
    private JobStatus jobStatus;
    private ExelExportBuilder exelExportBuilder;
    private int id;

    public Job(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        this.id = JOBS_COUNT.incrementAndGet();
        jobs.add(this);
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static CopyOnWriteArrayList<Job> getJobs() {
        return jobs;
    }

    public static void setJobs(CopyOnWriteArrayList<Job> jobs) {
        Job.jobs = jobs;
    }

    public static Job findJobById(int id){
        return jobs.stream().filter(job -> job.getId() == id).findAny().orElse(null);
    }

    public ExelExportBuilder getExelExportBuilder() {
        return exelExportBuilder;
    }

    public void setExelExportBuilder(ExelExportBuilder exelExportBuilder) {
        this.exelExportBuilder = exelExportBuilder;
    }
}
