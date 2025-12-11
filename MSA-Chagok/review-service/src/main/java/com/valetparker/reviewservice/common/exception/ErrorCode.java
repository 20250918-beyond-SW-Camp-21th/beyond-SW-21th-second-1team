package com.valetparker.reviewservice.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum ErrorCode {

    // 1xxxx: Command(등록/수정/삭제) 실패
    REVIEW_CREATE_FAILED("10001", "리뷰 등록에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_UPDATE_FAILED("10002", "리뷰 수정에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_DELETE_FAILED("10003", "리뷰 삭제에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 2xxxx: 조회/존재 여부
    REVIEW_NOT_FOUND("20001", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 3xxxx: 인증/인가/권한
    REVIEW_ACCESS_DENIED("30001", "리뷰에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 4xxxx: Validation/Business Rule
    INVALID_RATING("40001", "유효하지 않은 별점입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_REQUEST("40002", "리뷰 생성 요청이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("40010", "입력 값 검증 오류입니다.", HttpStatus.BAD_REQUEST),

    // 5xxxx: 내부 서버 오류
    INTERNAL_SERVER_ERROR("50001", "내부 서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}