package com.yogesh.jobconnect.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // get the role of the logged-in user
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("EMPLOYER".equals(auth.getAuthority())) {
                response.sendRedirect("/employer/dashboard");
                return;
            } else if ("JOB_SEEKER".equals(auth.getAuthority())) {
                response.sendRedirect("/seeker/jobs");
                return;
            }
        }

        // fallback (in case role not found)
        response.sendRedirect("/");
    }
}
