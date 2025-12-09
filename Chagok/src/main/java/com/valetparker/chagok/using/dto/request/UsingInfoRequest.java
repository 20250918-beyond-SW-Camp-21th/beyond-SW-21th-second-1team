package com.valetparker.chagok.using.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UsingInfoRequest {

    private long reservationId;
    private LocalDateTime currTime;

}
