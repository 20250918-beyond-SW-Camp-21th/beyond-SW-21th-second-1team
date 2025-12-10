package com.valetparker.reservationservice.command.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsedSpotUpdateResponse {
    private final boolean isUsing;
    private final Long parkinglotId;
}
