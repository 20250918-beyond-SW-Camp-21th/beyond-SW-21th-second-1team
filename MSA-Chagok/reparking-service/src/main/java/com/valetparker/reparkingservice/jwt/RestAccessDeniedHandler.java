package com.valetparker.reparkingservice.jwt;

import com.valetparker.reparkingservice.common.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//
//        ErrorCode errorCode = ErrorCode.ADMIN_ERROR;
//
//        String jsonResponse = "{\"error\": \"Forbidden\", \"message\": \"" + accessDeniedException.getMessage() + "\"}";
//        response.getWriter().write(jsonResponse);
//    }
@Override
public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // 1. 응답 헤더 설정
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    // 2. ErrorCode 가져오기
    ErrorCode errorCode = ErrorCode.ADMIN_ERROR;

    String jsonResponse = String.format("""
            {
                "status": "FAIL",
                "message": "%s",
                "errorCode": "%s",
                "data": null
            }
            """, errorCode.getMessage(), errorCode.getCode());

    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
}
}
