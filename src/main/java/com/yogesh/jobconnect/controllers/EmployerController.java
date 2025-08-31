package com.yogesh.jobconnect.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.models.JobApplication;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.security.CustomUserDetails;
import com.yogesh.jobconnect.services.JobService;

@Controller
public class EmployerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/employer/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("job", new Job()); // empty object for form
        return "employer-dashboard";
    }

    @PostMapping("/employer/addJob")
    public String addJob(@ModelAttribute Job job,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User employer = userRepository.findByUsername(userDetails.getUsername());
        job.setEmployer(employer);
        jobService.saveJob(job);
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/employer/editJob/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id); // fetch job by id
        model.addAttribute("job", job);
        return "edit-job"; // go to edit form
    }

    @PostMapping("/employer/updateJob/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute Job job) {
        Job existing = jobService.getJobById(id);
        if (existing != null) {
            existing.setTitle(job.getTitle());
            existing.setCompanyName(job.getCompanyName());
            existing.setDescription(job.getDescription());
            existing.setLocation(job.getLocation());
            existing.setDeadline(job.getDeadline());
            existing.setSalary(job.getSalary());
            jobService.saveJob(existing);
        }
        return "redirect:/employer/jobs";
    }

    @GetMapping("/employer/deleteJob/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/employer/applications/{jobId}")
    public String viewApplications(@PathVariable Long jobId, Model model) {
        Job job = jobService.getJobById(jobId);
        model.addAttribute("job", job);
        model.addAttribute("applications", job.getApplications());
        return "view-applications";
    }

    @GetMapping("/employer/updateStatus/{appId}/{status}")
    public String updateApplicationStatus(@PathVariable Long appId, @PathVariable String status) {
        JobApplication application = applicationRepository.findById(appId).orElse(null);
        if (application != null) {
            application.setStatus(status.toUpperCase()); // update status
            applicationRepository.save(application);
        }
        return "redirect:/employer/applications/" + (application != null ? application.getJob().getId() : "");
    }

    @GetMapping("/employer/jobs")
    public String viewMyJobs(Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User employer = userRepository.findByUsername(userDetails.getUsername());
        List<Job> jobs = jobService.getJobsByEmployer(employer);
        model.addAttribute("jobs", jobs);
        return "employer-jobs";
    }

}
