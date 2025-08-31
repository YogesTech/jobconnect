package com.yogesh.jobconnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yogesh.jobconnect.models.Job;
import com.yogesh.jobconnect.models.User;

// Repository for job postings
public interface JobRepository extends JpaRepository<Job, Long> {

    // find jobs by title containing a keyword OR location containing a keyword
    List<Job> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);

    List<Job> findByEmployer(User employer);

}
