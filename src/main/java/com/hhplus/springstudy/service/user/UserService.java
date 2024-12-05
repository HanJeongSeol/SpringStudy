package com.hhplus.springstudy.service.user;

import com.hhplus.springstudy.common.constant.ErrorCode;
import com.hhplus.springstudy.common.constant.RoleEnum;
import com.hhplus.springstudy.config.jwt.JwtTokenProvider;
import com.hhplus.springstudy.config.security.CustomUserDetails;
import com.hhplus.springstudy.domain.role.Role;
import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.user.UserLoginResponseDto;
import com.hhplus.springstudy.dto.user.UserRequestDto;
import com.hhplus.springstudy.dto.user.UserResponseDto;
import com.hhplus.springstudy.dto.user.UserSaveRequestDto;
import com.hhplus.springstudy.exception.BusinessException;
import com.hhplus.springstudy.repository.role.RoleRepository;
import com.hhplus.springstudy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 로그인 사용자 인증 객체 및 토큰 생성
     *
     * @param requestDto
     * @return
     */
    @Transactional
    public UserLoginResponseDto authenticateAndGenerateToken(UserRequestDto requestDto){
        // 1. 사용자 아이디, 비밀번호로 Authentication 객체 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUserId(),
                        requestDto.getUserPassword()
                )
        );
        // 2. 인증된 사용자 정보에서 UserDetails 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 3. 역할 정보 추출
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken = jwtTokenProvider.generateToken(userDetails.getUsername(), String.join(",", roles));


        return UserMapper.toLoginResponseDto(userDetails.getUsername(), userDetails.getUserName(), roles, accessToken);
    }

    /**
     * 회원가입
     *
     * @param requestDto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponseDto registerUser(UserSaveRequestDto requestDto) {
        // 1. 중복 회원 검사
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new BusinessException(ErrorCode.USER_ID_DUPLICATE_INPUT);
        }

        // 비밀번호 암호화 추가
        String encryptPassword = passwordEncoder.encode(requestDto.getUserPassword());

        // 2. User 엔티티 생성
        User user = new User(requestDto.getUserId(), encryptPassword, requestDto.getUserName());

        // 3. 입력받은 권한이 DB에 존재하는지 확인
        List<Role> roles = resolveRoles(requestDto.getRoleIds());

        // 4. User 객체에 UserRole 등록
        roles.forEach(role -> user.addRole(role));

        // 5. User 저장
        User saveUser = userRepository.save(user);

        // 6. 성공 메시지 반환
        return UserMapper.toResponseDto(saveUser);

    }


    /**
     * 회원 가입시 입력받은 권한이 DB에 존재하는지 확인.
     * 없다면 Default 값으로 USER 권한 부여.
     *
     * @param roleIds
     * @return
     */
    public List<Role> resolveRoles(List<String> roleIds){
        if(roleIds == null || roleIds.isEmpty()){
            roleIds = List.of(RoleEnum.USER.getId());
        }

        return roleIds.stream()
                .map(roleId -> {
                    return roleRepository.findByRoleId(roleId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_ENTITY_NOT_FOUND));
                })
                .toList();
    }

}
