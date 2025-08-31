package com.yogesh.jobconnect.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto id
    private Long id;

    @Column(nullable = false) // title required
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, length = 2000) // job description
    private String description;

    @Column(nullable = false) // job location
    private String location;

    @Column(nullable = false) // application deadline
    private String deadline;

    @Column(nullable = false)
    private String salary;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private User employer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

}
