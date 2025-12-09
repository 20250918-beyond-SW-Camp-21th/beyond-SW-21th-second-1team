package com.ohgiraffers.userservice.command.controller;

import com.ohgiraffers.userservice.command.dto.request.UserCreateRequest;
import com.ohgiraffers.userservice.command.dto.request.UserUpdateRequest;
import com.ohgiraffers.userservice.command.service.UserCommandService;
import com.ohgiraffers.userservice.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/regist")
    public ResponseEntity<ApiResponse<Void>> registerUser(@RequestBody UserCreateRequest request) {
        userCommandService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    @PostMapping("/user/modify")
    public ResponseEntity<ApiResponse<Void>> modifyUser(
            @AuthenticationPrincipal User header,
            @RequestBody UserUpdateRequest request) {

        String email = header.getUsername();
        System.out.println("email => " + email);
        System.out.println("request => " + request);

        userCommandService.updateUser(email, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

}
