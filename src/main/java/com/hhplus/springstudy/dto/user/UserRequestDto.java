package com.hhplus.springstudy.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String userId;
    private String userPassword;
}
