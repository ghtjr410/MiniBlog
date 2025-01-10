package com.miniblog.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.back.filter.CustomAuthenticationFilter;
import com.miniblog.back.service.TokenService;
import com.miniblog.back.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenService tokenService;
    private final String[] freeResourceUrls = {"/api/v1/members/register", "/api/v1/members/login"};
    private final String[] userOnlyResourceUrls = {"/api/v1/members/logout"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> { auth
                    .requestMatchers(freeResourceUrls).permitAll()
                    .requestMatchers(userOnlyResourceUrls).hasRole("user")
                    .anyRequest().authenticated();
                })
                .addFilterAt(customAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new CustomAuthenticationFilter(tokenService, authenticationManager);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
