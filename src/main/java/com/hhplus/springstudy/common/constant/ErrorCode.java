package com.hhplus.springstudy.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 공통 에러
    GLOBAL_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    GLOBAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    GLOBAL_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다."),
    GLOBAL_VALIDATION_ERROR(HttpStatus.BAD_REQUEST,"입력값이 유효하지 않습니다."),

    // 사용자 관련 에러 코드
    USER_ID_DUPLICATE_INPUT(HttpStatus.CONFLICT, "이미 사용중인 아이디입니다."),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),

    // 인증 관련 에러
    USER_ID_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    USER_PASSWORD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    JWT_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    JWT_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 요청에 없습니다."),

    // 게시글 관련 에러 코드
    POST_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),

    // 권한 관련 에러 코드
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    ROLE_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 권한이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

