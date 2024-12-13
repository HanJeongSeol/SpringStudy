package com.hhplus.springstudy.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * JWT 토큰에서 추출한 사용자 정보를 담는 클래스
 * Spring Security의 인증 객체에서 사용됨
 */
@Getter
public class CustomPrincipal {
    private String userId;
    private List<String> roles;

    public CustomPrincipal(String userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
