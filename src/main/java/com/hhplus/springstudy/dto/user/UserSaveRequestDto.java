package com.hhplus.springstudy.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String userId;
    private String userPassword;
    private String userName;
    private List<String> roleIds;  // ROLE_USER, ROLE_ADMIN
}
