package com.hhplus.springstudy.service.user;

import com.hhplus.springstudy.domain.user.User;
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
}
