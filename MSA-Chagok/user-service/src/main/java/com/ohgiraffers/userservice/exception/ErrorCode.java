package com.ohgiraffers.userservice.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {

    VALIDATION_ERROR("400", "요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_UPDATE_VALUES("400", "변경할 값 없음", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_MISSING("400", "필수 값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATED("400", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATED("400", "이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST),
    CAR_NUMBER_DUPLICATED("400", "이미 사용 중인 차량번호입니다.", HttpStatus.BAD_REQUEST),

    AUTHENTICATION_ERROR("401", "인증 실패 / 인증이 필요함", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS("401", "이메일 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_MISMATCH("401", "리프레시 토큰이 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("401", "리프레시 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),

    AUTHORIZATION_ERROR("403", "인가 실패 / 접근 권한이 필요함", HttpStatus.FORBIDDEN),

    USER_NOT_FOUND("404", "해당 유저를 찾을 수 없음", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_NOT_FOUND("404", "리프레시 토큰이 존재하지 않습니다.", HttpStatus.NOT_FOUND),


    SERVER_ERROR("500", "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);


    private String code;
    private String message;
    private HttpStatus httpStatus;


}
