package com.hhplus.springstudy.controller.user;

import com.hhplus.springstudy.common.constant.SuccessCode;
import com.hhplus.springstudy.common.response.ApiResponse;
import com.hhplus.springstudy.dto.user.UserRequestDto;
import com.hhplus.springstudy.dto.user.UserResponseDto;
import com.hhplus.springstudy.dto.user.UserSaveRequestDto;
import com.hhplus.springstudy.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@Valid @RequestBody UserSaveRequestDto requestDto){
        UserResponseDto responseDto = userService.registerUser(requestDto);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.USER_CREATE_SUCCESS, responseDto));
    }
d
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> loginUser(@RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.USER_LOGIN_SUCCESS, responseDto));
    }
}
