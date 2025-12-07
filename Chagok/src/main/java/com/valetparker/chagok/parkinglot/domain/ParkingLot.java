package com.valetparker.chagok.parkinglot.domain;

import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
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
    private Seouldistrict seoulDistrict;

    @Column(nullable = false)
    private Integer totalSpots;

    @Column(nullable = false)
    private Integer usedSpots;

    @Column(nullable = false)
    private Integer baseFee;

    @Column(nullable = false)
    private Integer baseTime;

    @Column(nullable = false)
    private Integer unitFee;

    @Column(nullable = false)
    private Integer unitTime;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Long adminId;


    @Builder
    public ParkingLot(
            String name,
            Seouldistrict seoulDistrict,
            String address,
            Integer baseFee,
            Integer unitFee,
            Integer totalSpots,
            Long adminId) {

        this.name = name;
        this.seoulDistrict = seoulDistrict;
        this.address = address != null ? address : "주소 미입력";
        this.baseFee = baseFee;
        this.unitFee = unitFee;
        this.totalSpots = totalSpots;
        this.usedSpots = 0;           // 초기화값.
        this.baseTime = 30;           // 30분.
        this.unitTime = 10;           // 10분 단위.
        this.averageRating = 0.0;
        this.adminId = adminId != null ? adminId : 1L;
    }

    //  남은 자리 수
    public int getRemainingSpots() {
        return this.totalSpots - this.usedSpots;
    }

    //  입차
    public void entryCar() {
        if (getRemainingSpots() <= 0) {
            throw new IllegalStateException("만차입니다");
        }
        this.usedSpots++;
    }

    //  출차
    public void exitCar() {
        if (this.usedSpots <= 0) {
            throw new IllegalStateException("출차할 차량이 없습니다");
        }
        this.usedSpots--;
    }

    //  주소 수정
    public void updateAddress(String newAddress) {
        if (newAddress == null || newAddress.isBlank()) {
            throw new IllegalArgumentException("주소는 필수입니다");
        }
        this.address = newAddress;
    }

    // 평점 업데이트
    public void updateAverageRating(double newRating) {
        if (newRating < 0.0 || newRating > 5.0) {
            throw new IllegalArgumentException("평점은 0~5.0 사이여야 합니다");
        }
        this.averageRating = newRating;
    }

}
