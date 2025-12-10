package com.valetparker.reparkingservice.query.dto;

import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import com.valetparker.reparkingservice.query.enums.ParkinglotSortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkinglotSearchRequest {

    private Integer page = 1;
    private Integer size = 10;
    private ParkinglotSortType sort = ParkinglotSortType.NAME_ASC;
    private SeoulDistrict seoulDistrict;

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }
}
