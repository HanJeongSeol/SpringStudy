package com.hhplus.springstudy.exception;

import com.hhplus.springstudy.common.constant.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode, String customMessage){
        super(customMessage);
        this.errorCode = errorCode;
        log.error("BusinessException 발생 - ErrorCode: {}, CustomMessage : {}", errorCode, customMessage);
    }
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        log.error("BusinessException 발생 - ErrorCode: {}, DefaultMessage : {}", errorCode, errorCode.getMessage());
    }
}