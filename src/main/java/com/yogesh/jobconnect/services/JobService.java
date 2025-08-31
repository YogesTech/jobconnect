package com.yogesh.jobconnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.models.JobApplication;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // add a new job
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // list all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // delete job by id
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<JobApplication> getApplicationsForJob(Long jobId) {
        Job job = getJobById(jobId);
        return job.getApplications();
    }

    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
    }

    public List<Job> getJobsByEmployer(User employer) {
        return jobRepository.findByEmployer(employer);
    }

}
