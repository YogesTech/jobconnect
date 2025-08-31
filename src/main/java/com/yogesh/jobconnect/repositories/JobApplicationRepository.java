package com.yogesh.jobconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yogesh.jobconnect.models.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
