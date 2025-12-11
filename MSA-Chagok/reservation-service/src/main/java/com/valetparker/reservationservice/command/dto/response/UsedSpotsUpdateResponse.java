package com.valetparker.reservationservice.command.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UsedSpotsUpdateResponse {
    private final boolean isUsing;
    private final Long parkinglotId;
}
