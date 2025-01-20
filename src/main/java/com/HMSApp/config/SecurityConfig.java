package com.HMSApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //h(cd)2
        http.csrf().disable().cors().disable();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        //haap

//        http.authorizeHttpRequests().anyRequest().permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/sign-up","/api/auth/login","/api/auth/property/sign-up","/api/auth/verify-otp","/api/auth/login-otp","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html")
                .permitAll()
                .requestMatchers("/api/v1/property/addProperty")
                .hasRole("OWNER")
                .requestMatchers("/api/v1/property/deleteProperty")
                .hasAnyRole("OWNER","ADMIN")
                .requestMatchers("/api/auth/blog/sign-up")
                .hasRole("ADMIN")
                .anyRequest().authenticated();

        return http.build();
    }
}
