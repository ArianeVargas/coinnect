package com.coinnect.registration_login.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coinnect.registration_login.common.Jwt.JwtTokenFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED, 
                "Código de error: 401 - No has iniciado sesión correctamente. " +
                "Este error ocurre cuando el usuario intenta acceder a un recurso que requiere autenticación, " +
                "pero no ha proporcionado credenciales válidas o su sesión ha expirado."
            );
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendError(
                HttpServletResponse.SC_FORBIDDEN, 
                "Código de error: 403 - No tienes permiso para acceder a este recurso. " +
                "Este error ocurre cuando el usuario está autenticado pero no tiene los permisos necesarios " +
                "para acceder al recurso solicitado."
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()  
            .requestMatchers("/register/**").permitAll() 
            .requestMatchers("/management/**").hasRole("ADMIN")  
            .anyRequest().authenticated()  
        )
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
        )
        .authenticationProvider(authenticationProvider)  
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); 

    return http.build();
    }
}
