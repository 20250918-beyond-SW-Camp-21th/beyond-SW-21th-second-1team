package com.valetparker.reparkingservice.common.dto;

import com.valetparker.reparkingservice.common.entity.Parkinglot;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParkinglotDto {

    private Long parkinglotId;
    private String name;
    private String address;
    private SeoulDistrict seoulDistrict;
    private Integer totalSpots;
    private Integer usedSpots;
    private Integer baseFee;
    private Integer baseTime;
    private Double avgRating;

    public static ParkinglotDto from(Parkinglot parkinglot) {
        return ParkinglotDto.builder()
                .parkinglotId(parkinglot.getParkinglotId())
                .name(parkinglot.getName())
                .address(parkinglot.getAddress())
                .seoulDistrict(parkinglot.getSeoulDistrict())
                .totalSpots(parkinglot.getTotalSpots())
                .usedSpots(parkinglot.getUsedSpots())
                .baseFee(parkinglot.getBaseFee())
                .baseTime(parkinglot.getBaseTime())
                .avgRating(parkinglot.getAvgRating())
                .build();
    }

}
