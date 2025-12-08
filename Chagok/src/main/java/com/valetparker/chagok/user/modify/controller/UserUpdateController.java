package com.valetparker.chagok.user.modify.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.user.command.dto.request.UserCreateRequest;
import com.valetparker.chagok.user.modify.dto.UserUpdateRequest;
import com.valetparker.chagok.user.modify.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateService userUpdateService;

    @PostMapping("/{userNo}/modify")
    public ResponseEntity<ApiResponse<Void>> modifyUser(@PathVariable Long userNo, @RequestBody UserUpdateRequest request) {
        userUpdateService.updateUser(userNo, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

}
