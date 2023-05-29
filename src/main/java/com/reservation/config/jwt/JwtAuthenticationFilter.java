package com.reservation.config.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    }
}
