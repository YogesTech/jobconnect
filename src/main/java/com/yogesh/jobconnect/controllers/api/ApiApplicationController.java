package com.yogesh.jobconnect.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.yogesh.jobconnect.models.JobApplication;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.services.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApiApplicationController {
    private final JobService jobService;
    private final UserRepository userRepository;
    private final JobApplicationRepository applicationRepository;

    public ApiApplicationController(JobService jobService, UserRepository userRepository, JobApplicationRepository applicationRepository) {
        this.jobService = jobService;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody ApplicationRequest request, Authentication authentication) {
        User applicant = userRepository.findByUsername(authentication.getName());
        var job = jobService.getJobById(request.jobId());
        if (job == null) return ResponseEntity.notFound().build();
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setUser(applicant);
        application.setCoverLetter(request.coverLetter());
        application.setResumeLink(request.resumeLink());
        JobApplication savedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(new ApplicationSummary(savedApplication.getId(), job.getId(), job.getTitle(), savedApplication.getStatus()));
    }

    @GetMapping("/mine")
    public List<ApplicationSummary> mine(Authentication authentication) {
        User applicant = userRepository.findByUsername(authentication.getName());
        return applicant.getApplications() == null ? List.of() : applicant.getApplications().stream()
                .map(application -> new ApplicationSummary(application.getId(), application.getJob().getId(), application.getJob().getTitle(), application.getStatus())).toList();
    }

    public record ApplicationRequest(Long jobId, String coverLetter, String resumeLink) {
    }

    public record ApplicationSummary(Long id, Long jobId, String jobTitle, String status) {
    }
}
