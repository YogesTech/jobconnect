package com.yogesh.jobconnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // will encrypt password

    // save a new user (signup)
    public User saveUser(User user) {
        // encrypting password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // find user by username (for login purpose)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
