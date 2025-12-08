package com.valetparker.chagok.user.command.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.user.command.dto.request.UserCreateRequest;
import com.valetparker.chagok.user.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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

}
