package com.hhplus.springstudy.config.security;

import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * [Security] 사용자 정보를 저장소에서 가져오는 클래스.
 * UserDetailsService를 상속받아 loadUserByUsername 재정의해 DB에 저장된 사용자 정보를 가져온다.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * DB에 저장된 사용자 정보를 가져오는 메서드다.
     * 사용자 테이블의 아이디 필드가 `userId`여도, 메서드 시그니처의 `username`은 변경하지 않는 것이 좋다.
     *
     * @param username  // 사용자 아이디(userId)
     * @return          // usetDetails를 구현한 CustomUserDetails 객체
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다." + username));

        return new CustomUserDetails(user);
    }
}
