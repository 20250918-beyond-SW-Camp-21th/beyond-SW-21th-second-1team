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
    REGIST_ERROR_TIME_CONFLICT("10003", "겹치는 시간입니다.",HttpStatus.INTERNAL_SERVER_ERROR),
    REGIST_ERROR_NO_PARKINGLOT("10004", "없는 주차장을 선택하셨습니다.",HttpStatus.INTERNAL_SERVER_ERROR),
    // 조회 관련 오류
    NOT_FOUND("20001", "조회에 실패하였습니다.",  HttpStatus.NOT_FOUND),
    RESERVATION_NOT_FOUND("20002", "예약 정보를 조회할 수 업습니다.", HttpStatus.NOT_FOUND),

    // request 값 입력 오류
    VALIDATION_ERROR("40001", "입력 값 검증 오류입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR_EARLY_START("40002", "예약 시간보다 이릅니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR_TIME_UNAVAILABLE("40003", "입력된 시간 정보가 잘못되었습니다.",HttpStatus.BAD_REQUEST ),
    VALIDATION_ERROR_WRONG_RESERVATIONID("40004", "해당 예약번호는 유효하지 않습니다.", HttpStatus.BAD_REQUEST ),

    // 기타 오류
    INTERNAL_SERVER_ERROR("50001", "내부 서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}