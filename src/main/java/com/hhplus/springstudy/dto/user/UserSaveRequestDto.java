package com.hhplus.springstudy.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 4, max =10, message = "아이디는 4자 이상, 10자 이하여야 합니다.")
    @Pattern(regexp ="^[a-z0-9]+$", message = "아이디는 소문자와 숫자만 사용 가능합니다.")
    private String userId;

    @NotBlank(message="비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 15, message="비밀번호는 8자 이상, 15자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자가 포함되야 합니다.")
    private String userPassword;
    private String userName;
    private List<String> roleIds;  // ROLE_USER, ROLE_ADMIN
}
