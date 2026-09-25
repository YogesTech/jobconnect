package com.yogesh.jobconnect.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.yogesh.jobconnect.controllers.api.ApiEmployerController;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.services.JobService;
import com.yogesh.jobconnect.security.CustomUserDetailsService;

@WebMvcTest(controllers = ApiEmployerController.class)
class EmployerControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private JobService jobService;
    @MockitoBean private UserRepository userRepository;
    @MockitoBean private JobApplicationRepository applicationRepository;
    @MockitoBean private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(username = "employer1", authorities = { "EMPLOYER" })
    void employerJobsEndpointReturnsJson() throws Exception {
        User employer = new User(); employer.setId(1L); employer.setUsername("employer1");
        when(userRepository.findByUsername("employer1")).thenReturn(employer);
        when(jobService.getJobsByEmployer(employer)).thenReturn(List.of());
        mockMvc.perform(get("/api/employer/jobs")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
