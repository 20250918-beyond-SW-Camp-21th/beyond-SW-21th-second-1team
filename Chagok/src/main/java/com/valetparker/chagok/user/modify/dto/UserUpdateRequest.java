package com.valetparker.chagok.user.modify.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequest {

    private String password;
    private String carNumber;

}
