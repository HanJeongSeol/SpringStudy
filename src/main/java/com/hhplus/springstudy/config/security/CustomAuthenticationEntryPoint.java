package com.hhplus.springstudy.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Spring Security 필터 체인에서 JWT 검증 실패 시 호출된다.
 * 인증되지 않은 사용자가 리소스 요청을 하는 경우 발생
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증 실패 로그 기록
        log.error("Authentication failed: {}", authException.getMessage());

        // HandlerExceptionResolver를 사용하여 GlobalExceptionHandler로 예외 위임
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
