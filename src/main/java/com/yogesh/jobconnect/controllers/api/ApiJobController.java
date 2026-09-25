package com.yogesh.jobconnect.controllers.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.services.JobService;

@RestController
@RequestMapping("/api/jobs")
public class ApiJobController {
    private final JobService jobService;
    public ApiJobController(JobService jobService) { this.jobService = jobService; }
    @GetMapping public List<JobSummary> list(@RequestParam(required = false) String search) {
        List<Job> jobs = search == null || search.isBlank() ? jobService.getAllJobs() : jobService.searchJobs(search.trim());
        return jobs.stream().map(ApiJobController::toSummary).toList();
    }
    @GetMapping("/{id}") public ResponseEntity<JobSummary> get(@PathVariable Long id) {
        Job job = jobService.getJobById(id); return job == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(toSummary(job));
    }
    private static JobSummary toSummary(Job job) { return new JobSummary(job.getId(), job.getTitle(), job.getCompanyName(), job.getDescription(), job.getLocation(), job.getDeadline(), job.getSalary()); }
    public record JobSummary(Long id, String title, String companyName, String description, String location, String deadline, String salary) {}
}
