package com.hhplus.springstudy.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Spring Security 필터 체인에서 인증이 완료되었지만 리소스 접근 권한이 없을 때 호출된다.
 */
@Slf4j
@Component
//@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver")HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 로그 기록
        log.error("Access denied: {}", accessDeniedException.getMessage());

        // HandlerExceptionResolver를 통해 GlobalExceptionHandler로 예외 위임
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}
