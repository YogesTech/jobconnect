package com.yogesh.jobconnect.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.yogesh.jobconnect.repositories.UserRepository;
import com.yogesh.jobconnect.repositories.JobApplicationRepository;
import com.yogesh.jobconnect.services.JobService;
import com.yogesh.jobconnect.security.CustomUserDetailsService;
import com.yogesh.jobconnect.security.CustomSuccessHandler;

@WebMvcTest(controllers = EmployerController.class)
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock dependencies
    @MockitoBean
    private JobService jobService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JobApplicationRepository applicationRepository;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private CustomSuccessHandler customSuccessHandler;

    // Simple test to check if Employer Dashboard loads
    @Test
    @WithMockUser(username = "employer1", authorities = { "EMPLOYER" })
    void testEmployerDashboardLoads() throws Exception {
        mockMvc.perform(get("/employer/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("employer-dashboard"));
    }
}
