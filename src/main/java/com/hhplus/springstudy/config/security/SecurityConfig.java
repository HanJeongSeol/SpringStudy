package com.hhplus.springstudy.config.security;

import com.hhplus.springstudy.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    //Http Methpd : Get 인증예외 List
    private String[] AUTH_GET_WHITELIST = {
            "/api/posts"    // 모든 게시글 조회
    };

    // 인증 예외 List
    private String[] AUTH_WHITELIST = {
            "/api/users/**" // 회원가입 및 로그인

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)    // JWT 사용 시 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()  // 인증이 필요없는 요청
                        .requestMatchers(HttpMethod.GET, AUTH_GET_WHITELIST).permitAll()
                        .anyRequest().authenticated()   // 나머지 요청에 대해서 인증 필요
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // JWT 토큰 사용으로 세션 사용 안함
        return http.build();
    }

    /**
     * 인증을 관리하는 기본 매니저 등록.
     * 로그인 시 사용자가 입력한 아이디와 비밀번호 검증하고 인증 처리
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    /**
     * 비밀번호 해싱 및 검증
     * 암호화된 비밀번호를 저장하고, 인증 시 입력한 비밀번호와 비교
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

