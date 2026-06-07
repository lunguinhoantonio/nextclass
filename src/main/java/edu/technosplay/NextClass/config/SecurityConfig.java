package edu.technosplay.NextClass.config;

import edu.technosplay.NextClass.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/nextclass/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**",
            "/h2-console/**",
            "/",
            "/cursos",
            "/cadastro",
            "/login",
            "/atendimento",
            "/atividades",
            "/css/**",
            "/js/**",
            "/assets/**",
            "/favicon.ico"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/nextclass/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/nextclass/atendimentos/publico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/nextclass/cursos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/nextclass/cursos/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.PUT, "/nextclass/cursos/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.PATCH, "/nextclass/cursos/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.DELETE, "/nextclass/cursos/**").hasRole("COORDENADOR")
                        .requestMatchers("/nextclass/usuarios/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.GET, "/nextclass/atendimentos/**")
                        .hasAnyRole("ATENDENTE", "COORDENADOR", "PROFESSOR", "ALUNO")
                        .requestMatchers(HttpMethod.PUT, "/nextclass/atendimentos/**")
                        .hasAnyRole("ATENDENTE", "COORDENADOR")
                        .requestMatchers(HttpMethod.PATCH, "/nextclass/atendimentos/**")
                        .hasAnyRole("ATENDENTE", "COORDENADOR")
                        .requestMatchers(HttpMethod.DELETE, "/nextclass/atendimentos/**")
                        .hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.GET, "/nextclass/turmas/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/nextclass/turmas/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.DELETE, "/nextclass/turmas/**").hasRole("COORDENADOR")
                        .requestMatchers(HttpMethod.POST, "/nextclass/matriculas/**").hasRole("ALUNO")
                        .requestMatchers(HttpMethod.GET, "/nextclass/matriculas/**")
                        .hasAnyRole("ALUNO", "COORDENADOR", "ATENDENTE")
                        .requestMatchers(HttpMethod.DELETE, "/nextclass/matriculas/**")
                        .hasAnyRole("ALUNO", "COORDENADOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
