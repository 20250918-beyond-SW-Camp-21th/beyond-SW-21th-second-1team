package com.valetparker.reparkingservice.query.dto;

import com.valetparker.reparkingservice.common.dto.ParkinglotDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkinglotDetailResponse {

    private final ParkinglotDto parkinglotDto;

}
