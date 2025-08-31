package com.yogesh.jobconnect.services;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.JobRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveJob() {
        Job job = new Job();
        job.setTitle("Java Developer");

        when(jobRepository.save(job)).thenReturn(job);

        Job savedJob = jobService.saveJob(job);

        assertThat(savedJob.getTitle()).isEqualTo("Java Developer");
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testGetJobById() {
        Job job = new Job();
        job.setId(1L);
        job.setTitle("Spring Boot Dev");

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job foundJob = jobService.getJobById(1L);

        assertThat(foundJob.getTitle()).isEqualTo("Spring Boot Dev");
    }

    @Test
    void testGetJobsByEmployer() {
        User employer = new User();
        employer.setId(1L);

        Job job1 = new Job();
        job1.setTitle("Backend Dev");

        Job job2 = new Job();
        job2.setTitle("Frontend Dev");

        when(jobRepository.findByEmployer(employer)).thenReturn(Arrays.asList(job1, job2));

        List<Job> jobs = jobService.getJobsByEmployer(employer);

        assertThat(jobs).hasSize(2);
        verify(jobRepository, times(1)).findByEmployer(employer);
    }
}
