package com.sparta.msa_assign.eureka.client.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain 빈 등록 - securityFilterChain은 Spring Security의 보안 필터 체인을 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호를 비활성화. CSRF 보호는 주로 브라우저 클라이언트를 대상으로 하는 공격을 방지하기 위해 사용 됨
                .csrf(csrf -> csrf.disable())
                // 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // /auth/signIn 경로에 대한 접근 허용. 해당 경로는 인증 없이 접근 가능
                        .requestMatchers("/members/**").permitAll()
                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated())
                // 세션 관리 정책을 정의합니다. 여기서는 세션을 사용하지 않도록 STATELESS로 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        // 설정된 보안 필터 체인을 반환
        return http.build();
    }
}
