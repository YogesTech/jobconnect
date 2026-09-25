package com.yogesh.jobconnect.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.yogesh.jobconnect.models.User;
import com.yogesh.jobconnect.services.UserService;
import com.yogesh.jobconnect.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public ApiAuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        return new LoginResponse(jwtService.generate((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal()), UserView.from(userService.findByUsername(request.username())));
    }

    @PostMapping("/signup")
    public UserView signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role() == null ? "JOB_SEEKER" : request.role());
        return UserView.from(userService.saveUser(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserView> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(UserView.from(userService.findByUsername(authentication.getName())));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    public record LoginRequest(String username, String password) {
    }

    public record SignupRequest(String username, String password, String role) {
    }

    public record UserView(Long id, String username, String role) {
        static UserView from(User user) {
            return new UserView(user.getId(), user.getUsername(), user.getRole());
        }
    }

    public record LoginResponse(String token, UserView user) {
    }
}
