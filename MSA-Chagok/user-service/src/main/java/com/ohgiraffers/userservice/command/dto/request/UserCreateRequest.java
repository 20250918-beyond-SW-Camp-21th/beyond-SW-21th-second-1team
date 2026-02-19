package com.ohgiraffers.userservice.command.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

    private final String email;

    private final String password;

    @JsonProperty("user_name")
    private final String userName;

    private final String nickname;

    @JsonProperty("car_number")
    private final String carNumber;

}
