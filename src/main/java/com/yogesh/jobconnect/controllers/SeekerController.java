package com.yogesh.jobconnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogesh.jobconnect.models.JobApplication;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.services.JobService;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.security.CustomUserDetails;

@Controller
public class SeekerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/seeker/jobs")
    public String viewJobs(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("jobs", jobService.searchJobs(search));
        } else {
            model.addAttribute("jobs", jobService.getAllJobs());
        }
        return "seeker-jobs";
    }

    @GetMapping("/seeker/apply/{jobId}")
    public String showApplicationForm(@PathVariable Long jobId, Model model) {
        JobApplication application = new JobApplication();
        application.setJob(jobService.getJobById(jobId)); // pre-set job
        model.addAttribute("application", application);
        return "apply-job";
    }

    @PostMapping("/seeker/submitApplication")
    public String submitApplication(@ModelAttribute JobApplication application,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        application.setUser(user); // set the logged-in job seeker
        applicationRepository.save(application);
        return "redirect:/seeker/jobs";
    }

    @GetMapping("/seeker/myApplications")
    public String myApplications(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("applications", user.getApplications());
        return "my-applications";
    }

}
