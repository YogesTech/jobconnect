package com.yogesh.jobconnect.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "applications")
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relation to job
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // relation to user (job seeker)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 2000)
    private String coverLetter; // reason for applying

    @Column(nullable = false)
    private String resumeLink; // link to resume (Google Drive, etc.)

    @Column(nullable = false)
    private String status = "APPLIED"; // default status
}
