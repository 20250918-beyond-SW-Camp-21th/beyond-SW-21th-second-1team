package com.valetparker.reservationservice.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsedSpotsUpdateRequest {
    private final boolean isUsing;
    private final Long parkinglotId;
}
