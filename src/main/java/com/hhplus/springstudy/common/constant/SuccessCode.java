package com.hhplus.springstudy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    // 사용자 관련 성공 코드
    USER_READ_SUCCESS(HttpStatus.OK, "사용자 정보 조회 성공"),
    USER_CREATE_SUCCESS(HttpStatus.OK, "사용자 생성 성공"),
    USER_LOGIN_SUCCESS(HttpStatus.OK,"사용자 로그인 성공"),

    // 게시물 관련 성공 코드
    BOARD_ALL_READ_SUCCESS(HttpStatus.OK, "전체 게시물 조회 성공 "),
    BOARD_READ_SUCCESS(HttpStatus.OK, "게시물 조회 성공"),
    BOARD_CREATE_SUCCESS(HttpStatus.CREATED, "게시물 등록 성공"),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK, "게시물 수정 성공"),
    BOARD_DELETE_SUCCESS(HttpStatus.OK, "게시물 삭제 성공");


    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }
}
