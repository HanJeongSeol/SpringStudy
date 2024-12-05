package com.hhplus.springstudy.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseDto {
    private String userId;
    private String userName;
    private List<String> roles;

    public UserResponseDto(String userId, String userName, List<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }
}
