package com.hhplus.springstudy.service.user;

import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.user.UserLoginResponseDto;
import com.hhplus.springstudy.dto.user.UserResponseDto;

import java.util.List;

public class UserMapper {
    public static UserResponseDto toResponseDto(User user){
        List<String> roleIds = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleId())
                .toList();

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .roles(roleIds)
                .build();
    }

    public static UserLoginResponseDto toLoginResponseDto(String userId, String userName, List<String> roles, String accessToken){
        return UserLoginResponseDto.builder()
                .userId(userId)
                .userName(userName)
                .roles(roles)
                .accessToken(accessToken)
                .build();
    }
}
