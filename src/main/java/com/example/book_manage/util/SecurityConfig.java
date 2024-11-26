package com.example.book_manage.util;

import com.example.book_manage.user.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService; // CustomUserDetailsService 주입

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/register", "/css/style.css","script.js", "/login","/api/signup", "/api/login").permitAll() // 로그인 및 회원가입 페이지는 누구나 접근 가능
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                ) // 리다이렉트 방지
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 URL
                        .failureUrl("/login?error=true") // 실패 시 리디렉션할 URL
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // userDetailsService 사용
        return authenticationManagerBuilder.build();
    }
}
