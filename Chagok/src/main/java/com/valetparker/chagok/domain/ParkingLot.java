package com.valetparker.chagok.domain;

import com.valetparker.chagok.enums.SeoulDistrict;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkinglotId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeoulDistrict district;

    @Column(nullable = false)
    private Integer totalSpots;

    @Column(nullable = false)
    private Integer usedSpots;

    @Column(nullable = false)
    private Integer baseFee;

    @Column(nullable = false)
    private Integer baseTime;

    @Column(nullable = false)
    private Integer unitFee;        // -> 추가요금(10분당 천원 등)

    @Column(nullable = false)
    private Integer unitTime;


    @Column(nullable = false)       // -> 검색해보니 매번 쿼리 계산말고, 리뷰 등록 시 업데이트하는 방식을 추천 추천하더라구요.
    private Double averageRating;

    @Column(nullable = false)
    private Long adminId;

    @Builder
    public ParkingLot(
            String name
            , SeoulDistrict district
            , Integer baseFee
            , Integer unitFee
            , Integer totalSpots) {
        this.name = name;
        this.district = district;
        this.baseFee = baseFee;
        this.unitFee = unitFee;
        this.totalSpots = totalSpots;
        this.baseTime = 30; // 추가요금 30분
        this.unitTime = 60; // 추가요금 1시간
        this.averageRating = 0.0;
    }


    public int getRemainingSpots() {        // => 남은 자리 메소드
        return this.totalSpots - this.usedSpots;
    }

    public void nowusedSpots(Integer usedSpots) {
        this.usedSpots = usedSpots;
    }

    public void parkingLotAddress(String address){
        this.address = address;
    }



}
