package com.ohgiraffers.userservice.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequest {

    private String password;
    private String carNumber;

}
