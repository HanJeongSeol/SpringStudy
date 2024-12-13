package com.hhplus.springstudy.config.security;

import com.hhplus.springstudy.config.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

/**
 * JWT 인증 필터는 요청 당 한 번만 실행되는 것이 적합하다.
 * OncePerRequestFilter는 SpringSecurity에서 제공하�� 요청 당 한 번만 실행되는 필터이다.
 * 해당 클래스를 상속받아서 구현하자.
 */
@Slf4j
@Component
// wtAuthenticationFilter가 HandlerExceptionResolver를 주입받아 예외를 처리합니다.
//Spring 컨텍스트에 HandlerExceptionResolver 타입의 빈이 2개 이상 등록되어 있어서, Spring이 어떤 빈을 주입해야 할지 모호하여 오류가 발생합니다.
// **Lombok의 @RequiredArgsConstructor**가 생성자를 생성할 때 @Qualifier와 같은 애노테이션을 처리하지 못해서 발생
// 따라서 생성자를 명시적으로 작성하는 방법으로 진행
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver")HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 0. SecurityContext에 인증 정보가 이미 있다면 JWT 검증 스킵
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // 1. Authorization 헤더에서 JWT 추출
                String jwt = extractJwtFromRequest(request);

                // 2. JWT 검증 및 사용자 정보 설정
                if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                    // 3. 사용자 정보 추출
                    String username = jwtTokenProvider.extractUserId(jwt);
                    String roles = jwtTokenProvider.extractRoles(jwt);
                    //  EX > "ROLE_USER, ROLE_ADMIN" -> ["ROLE_USER", "ROLE_ADMIN"] 형태로 변환
                    List<String> roleList = List.of(roles.split(","));

                    // CustomPrincipal 생성
                    CustomPrincipal customPrincipal = new CustomPrincipal(username, roleList);

                    // 4. 인증 객체 생성 및 SecurityContext에 설정
                    // CustomPrincipal.getAuthorities() : GrantedAuthority 타입으로 변환된 역할 목록을 반환
                    // EX > ["ROLE_USER", "ROLE_ADMIN"] -> [SimpleGrantedAuthority("ROLE_USER"), SimpleGrantedAuthority("ROLE_ADMIN")]  
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customPrincipal, null, customPrincipal.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // 5. 다음 필터로 요청 전달
            filterChain.doFilter(request, response);

        } catch(Exception ex){
            // 6. 예외 발생 시 HandlerExceptionResolver로 위임
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    /**
     * Authorization 헤더에서 JWT 토큰 추출하는 메서드
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        log.warn("유효하지 않은 헤더 인증 요청 : {}", bearerToken);
        return null;
    }
}
