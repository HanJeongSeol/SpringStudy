package com.hhplus.springstudy.config.security;

import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.domain.user_role.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 스프링 시큐리티가 인증 및 권한 처리하는 데 필요한 사용자 정보를 제고앟는 역할
 * 사용자 인증에 필요한 username, password 제공
 * 사용자가 가진 역할 정보 제공
 * 계정 상태 반환
 */
@Getter
public class CustomUserDetails implements UserDetails {

    // 1. 테이블에 사용자 상태 컬럼이 추가되는 경우, 상태 필드 변수를 추가해서 사용하면 된다.
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    // 2. DB에서 조회한 User 객체를 기반으로 UserDetails에 필요한 데이터 설정
    // 3. userRiles를 권한 객체(GrantedAuthority)로 변환하여 authorities 필드에 설정
    public CustomUserDetails(User user){
        this.username = user.getUserId();
        this.password = user.getUserPassword();
        this.authorities = mapRolesToAuthorites(user.getUserRoles());
    }

    /**
     * 사용자 역할 정보를 스프링 시큐리티의 권한 객체(GrantedAuthority)로 변환
     *
     * @param userRoles
     * @return Collection<? extends GrantedAuthority> // GrantedAuthority의 구현체(SimpleGrantedAuthority) 목록
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorites(Collection<UserRole> userRoles){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole userRole : userRoles){
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleId()));
        }
        return authorities;
    }

    /**
     * 사용자의 권한 목록 반환 메서드
     * 사용자 요청에 대한 권한 검사를 수행할 때 사용
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
