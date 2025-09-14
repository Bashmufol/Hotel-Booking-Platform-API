package com.bash.hotel_booking_platfom.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                // Disable CSRF since we are using JWTs for stateless authentication
                .csrf(AbstractHttpConfigurer::disable)
                // Define public and protected endpoints
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated())
                // Enforce stateless sessions; authentication is handled solely by JWTs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add our custom JWT filter before Spring’s username/password authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Delegates authentication logic to Spring Security’s authentication manager
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt for password hashing and salting
        return new BCryptPasswordEncoder();
    }
}
