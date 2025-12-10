package com.valetparker.reparkingservice.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseInfoResponse {
    private final Long parkinglotId;
    private final String name;
    private final Integer baseFee;
    private final Integer baseTime;
}
