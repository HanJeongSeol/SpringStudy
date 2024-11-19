package com.hhplus.springstudy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 사용자 관련 에러 코드
    USER_ID_DUPLICATE_INPUT(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다."),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),

    USER_ID_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    USER_PASSWORD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // 게시글 관련 에러 코드
    POST_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

