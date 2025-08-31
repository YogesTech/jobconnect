package com.yogesh.jobconnect.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // table name = users
@Data // Lombok
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment id
    private Long id;

    @Column(nullable = false, unique = true) // username must be unique
    private String username;

    @Column(nullable = false) // password cannot be null
    private String password;

    @Column(nullable = false) // role can be JOB_SEEKER or EMPLOYER
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

}
