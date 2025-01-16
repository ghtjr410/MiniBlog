package com.miniblog.back.auth.config;

import com.miniblog.back.auth.filter.CustomAuthenticationFilter;
import com.miniblog.back.auth.filter.CustomTokenFilter;
import com.miniblog.back.auth.service.AuthService;
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
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthService authService;
    private final CustomTokenFilter customTokenFilter;
    private final SecurityProperties securityProperties;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> { auth
                    .requestMatchers(securityProperties.getPermitAllUrlsAsArray()).permitAll()
                    .requestMatchers(securityProperties.getUserOnlyUrlsAsArray()).hasRole("USER")
                    .anyRequest().authenticated();
                })
                .addFilterBefore(customTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(customAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new CustomAuthenticationFilter(authService, authenticationManager);
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
