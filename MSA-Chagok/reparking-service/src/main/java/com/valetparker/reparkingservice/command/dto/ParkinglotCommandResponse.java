package com.valetparker.reparkingservice.command.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkinglotCommandResponse {
    private Long parkinglotId;
}
