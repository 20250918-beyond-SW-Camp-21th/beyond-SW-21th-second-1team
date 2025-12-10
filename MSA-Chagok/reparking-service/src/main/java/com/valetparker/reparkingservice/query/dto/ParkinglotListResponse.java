package com.valetparker.reparkingservice.query.dto;

import com.valetparker.reparkingservice.common.dto.Pagination;
import com.valetparker.reparkingservice.common.dto.ParkinglotDto;
import com.valetparker.reparkingservice.query.enums.ParkinglotSortType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ParkinglotListResponse {

    private final List<ParkinglotDto> parkinglotDtoList;
    private final Pagination pagination;
    private final ParkinglotSortType sortType;
}
