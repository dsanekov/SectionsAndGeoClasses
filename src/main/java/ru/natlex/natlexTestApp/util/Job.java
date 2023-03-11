package ru.natlex.natlexTestApp.util;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private static int JOBS_COUNT = 0;
    private static List<Job> jobs = new ArrayList<>();
    private JobStatus jobStatus;
    private int id;
    private ModelAndView modelAndView;

    public Job(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        this.id = ++JOBS_COUNT;
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

    public static List<Job> getJobs() {
        return jobs;
    }

    public static void setJobs(List<Job> jobs) {
        Job.jobs = jobs;
    }

    public static Job findJobById(int id){
        return jobs.stream().filter(job -> job.getId() == id).findAny().orElse(null);
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }
}
