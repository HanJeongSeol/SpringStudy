package com.hhplus.springstudy.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserLoginResponseDto {
    private String userId;
    private String userName;
    private List<String> roles;
    private String accessToken;

    public UserLoginResponseDto(String userId, String userName, List<String> roles, String accessToken) {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
        this.accessToken = accessToken;
    }
}
