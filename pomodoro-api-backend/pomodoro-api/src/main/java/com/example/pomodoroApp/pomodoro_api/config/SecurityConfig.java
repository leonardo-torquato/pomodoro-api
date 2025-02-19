package com.example.pomodoroApp.pomodoro_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/pomodoros/**").permitAll() // Permite acesso público ao endpoint de Pomodoros
                        .anyRequest().authenticated() // Requer autenticação para outras rotas
                )
                .csrf(AbstractHttpConfigurer::disable); // Desabilita CSRF de forma compatível com Spring Security 6.1

        return http.build();
    }
}
