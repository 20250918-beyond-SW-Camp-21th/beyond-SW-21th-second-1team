package com.valetparker.reparkingservice.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsedSpotsUpdateRequest {
    private final boolean isUsing;
    private final Long parkinglotId;
}
