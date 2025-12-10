package com.valetparker.reservationservice.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {
    // 등록,수정,삭제 관련 오류
    REGIST_ERROR("10001", "등록에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),


    // 조회 관련 오류
    NOT_FOUND("20001", "조회에 실패하였습니다.",  HttpStatus.NOT_FOUND),

    // request 값 입력 오류
    VALIDATION_ERROR("40001", "입력 값 검증 오류입니다.", HttpStatus.BAD_REQUEST),

    LOGIN_ERROR("40002", "아이디 혹은 비밀번호 오류입니다.", HttpStatus.BAD_REQUEST),



    // 기타 오류
    INTERNAL_SERVER_ERROR("50001", "내부 서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}