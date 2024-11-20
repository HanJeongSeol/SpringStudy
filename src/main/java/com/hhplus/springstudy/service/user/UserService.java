package com.hhplus.springstudy.service.user;

import com.hhplus.springstudy.common.constant.ErrorCode;
import com.hhplus.springstudy.common.constant.RoleEnum;
import com.hhplus.springstudy.domain.role.Role;
import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.user.UserRequestDto;
import com.hhplus.springstudy.dto.user.UserResponseDto;
import com.hhplus.springstudy.dto.user.UserSaveRequestDto;
import com.hhplus.springstudy.exception.BusinessException;
import com.hhplus.springstudy.repository.role.RoleRepository;
import com.hhplus.springstudy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponseDto registerUser(UserSaveRequestDto requestDto) {
        // 1. 중복 회원 검사
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new BusinessException(ErrorCode.USER_ID_DUPLICATE_INPUT);
        }

        // 2. User 엔티티 생성
        User user = new User();
        user.setUserId(requestDto.getUserId());
        user.setUserPassword(requestDto.getUserPassword());
        user.setUserName(requestDto.getUserName());

        // 3. 입력받은 권한이 DB에 존재하는지 확인
        List<String> roleIds = requestDto.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) {
            roleIds = List.of(RoleEnum.USER.getId());
        }

        List<Role> roles = roleIds.stream()
                .map(roleId -> {
                    // 실제 Role 테이블에 데이터가 존재하는지 확인
                    return roleRepository.findByRoleId(roleId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_ENTITY_NOT_FOUND));
                })
                .toList();

        // 4. User 객체에 UserRole 등록
        roles.forEach(role -> user.addRole(role));

        // 5. User 저장
        User saveUser = userRepository.save(user);

        // 6. 성공 메시지 반환
        return UserMapper.toResponseDto(saveUser);

    }

    @Transactional
    public UserResponseDto loginUser(UserRequestDto requestDto){

        User user = userRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ID_NOT_FOUND));
        if(!user.getUserPassword().equals(requestDto.getUserPassword())){
            throw new BusinessException(ErrorCode.USER_PASSWORD_UNAUTHORIZED);
        }
        return UserMapper.toResponseDto(user);
    }
}
