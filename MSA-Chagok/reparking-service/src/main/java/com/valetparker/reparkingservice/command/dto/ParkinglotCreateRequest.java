package com.valetparker.reparkingservice.command.dto;

import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ParkinglotCreateRequest {

    private final String name;
    private final String address;
    private final SeoulDistrict seoulDistrict;
    private final Integer totalSpots;
    private final Integer baseFee;

}
