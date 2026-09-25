package com.yogesh.jobconnect.controllers.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.models.JobApplication;
import com.yogesh.jobconnect.services.JobService;

@RestController
@RequestMapping("/api/employer")
public class ApiEmployerController {
    private final JobService jobService; private final UserRepository userRepository; private final JobApplicationRepository applicationRepository;
    public ApiEmployerController(JobService jobService, UserRepository userRepository, JobApplicationRepository applicationRepository) { this.jobService = jobService; this.userRepository = userRepository; this.applicationRepository = applicationRepository; }
    @GetMapping("/jobs")
    public List<JobSummary> jobs(Authentication authentication) { User employer = userRepository.findByUsername(authentication.getName()); return jobService.getJobsByEmployer(employer).stream().map(job -> new JobSummary(job.getId(), job.getTitle(), job.getCompanyName(), job.getLocation(), job.getSalary())).toList(); }
    @PostMapping("/jobs")
    public ResponseEntity<JobSummary> create(@RequestBody JobRequest request, Authentication authentication) { User employer = userRepository.findByUsername(authentication.getName()); Job job = new Job(); job.setTitle(request.title()); job.setCompanyName(request.companyName()); job.setDescription(request.description()); job.setLocation(request.location()); job.setDeadline(request.deadline()); job.setSalary(request.salary()); job.setEmployer(employer); Job saved = jobService.saveJob(job); return ResponseEntity.ok(new JobSummary(saved.getId(), saved.getTitle(), saved.getCompanyName(), saved.getLocation(), saved.getSalary())); }
    public record JobRequest(String title, String companyName, String description, String location, String deadline, String salary) {}
    public record JobSummary(Long id, String title, String companyName, String location, String salary) {}
    @PutMapping("/jobs/{id}") public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobRequest request, Authentication authentication) {
        Job job = jobService.getJobById(id); User employer = userRepository.findByUsername(authentication.getName());
        if (job == null) return ResponseEntity.notFound().build(); if (job.getEmployer() == null || !job.getEmployer().getId().equals(employer.getId())) return ResponseEntity.status(403).body("You cannot edit this job");
        job.setTitle(request.title()); job.setCompanyName(request.companyName()); job.setDescription(request.description()); job.setLocation(request.location()); job.setDeadline(request.deadline()); job.setSalary(request.salary()); jobService.saveJob(job); return ResponseEntity.ok(new JobSummary(job.getId(), job.getTitle(), job.getCompanyName(), job.getLocation(), job.getSalary()));
    }
    @DeleteMapping("/jobs/{id}") public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        Job job = jobService.getJobById(id); User employer = userRepository.findByUsername(authentication.getName());
        if (job == null) return ResponseEntity.notFound().build(); if (job.getEmployer() == null || !job.getEmployer().getId().equals(employer.getId())) return ResponseEntity.status(403).build(); jobService.deleteJob(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/applications") public List<ApplicationSummary> applications(Authentication authentication) { User employer = userRepository.findByUsername(authentication.getName()); return applicationRepository.findAll().stream().filter(application -> application.getJob().getEmployer().getId().equals(employer.getId())).map(application -> toApplication(application)).toList(); }
    @PatchMapping("/applications/{id}/status") public ResponseEntity<?> status(@PathVariable Long id, @RequestBody StatusRequest request, Authentication authentication) { JobApplication application = applicationRepository.findById(id).orElse(null); User employer = userRepository.findByUsername(authentication.getName()); if (application == null) return ResponseEntity.notFound().build(); if (!application.getJob().getEmployer().getId().equals(employer.getId())) return ResponseEntity.status(403).build(); application.setStatus(request.status().toUpperCase()); applicationRepository.save(application); return ResponseEntity.ok(toApplication(application)); }
    private static ApplicationSummary toApplication(JobApplication application) { return new ApplicationSummary(application.getId(), application.getJob().getId(), application.getJob().getTitle(), application.getUser().getUsername(), application.getCoverLetter(), application.getResumeLink(), application.getStatus()); }
    public record StatusRequest(String status) {}
    public record ApplicationSummary(Long id, Long jobId, String jobTitle, String applicantUsername, String coverLetter, String resumeLink, String status) {}
}
