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
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomPrincipal(String userId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    /**
     * 콤마로 구분된 role 문자열을 GrantedAuthority 컬렉션으로 변환
     * EX: "ROLE_USER, ROLE_ADMIN" -> [SimpleGrantedAuthority("ROLE_USER"), SimpleGrantedAuthority("ROLE_ADMIN ")]
     */
    public Collection<? extends GrantedAuthority> convertRolesToAuthorities(String roles) {
        List<String> roleList = Arrays.asList(roles.split(","));
        return roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
