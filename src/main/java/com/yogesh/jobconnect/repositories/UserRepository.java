package com.yogesh.jobconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yogesh.jobconnect.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // custom query method to find user by username
    User findByUsername(String username);
}
