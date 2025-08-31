package com.yogesh.jobconnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // show signup page
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User()); // empty user object for form
        return "signup"; // Thymeleaf template name
    }

    // process signup form
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userService.saveUser(user); // saves user with encrypted password
        return "redirect:/login"; // after signup, redirect to login
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // show login.html
    }

}
