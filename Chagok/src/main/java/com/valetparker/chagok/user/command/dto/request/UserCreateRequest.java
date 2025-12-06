package com.valetparker.chagok.user.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

    private final String email;
    private final String password;
    private final String userName;
    private final String nickName;
    private final String carNumber;

}
