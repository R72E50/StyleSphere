package com.example.StyleSphere.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
        //http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        //Authenticate end Points
        http.authorizeHttpRequests( auth -> auth.requestMatchers("/product", "/auth/register", "/auth/login", "/auth/verify","/error","auth/forgot","auth/reset","/websocket","/websocket/**").permitAll().anyRequest().authenticated());
        return http.build();
    }


}
