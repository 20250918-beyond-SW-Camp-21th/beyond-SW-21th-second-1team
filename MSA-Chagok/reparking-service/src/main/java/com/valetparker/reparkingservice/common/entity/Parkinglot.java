package com.valetparker.reparkingservice.common.entity;

import com.valetparker.reparkingservice.command.dto.ParkinglotUpdateRequest;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
* !!!!!!!!!!!주의!!!!!!!!!!
* 앞으로 모든 Parkinglot에서 'l'은 소문자로 쓴다.
* */
@Entity
@Table(name = "tbl_parkinglot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Parkinglot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkinglotId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeoulDistrict seoulDistrict;

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

    @Column
    private Double avgRating;


    public static Parkinglot create(
            String name,
            String address,
            SeoulDistrict seoulDistrict,
            Integer totalSpots,
            Integer baseFee,
            Integer unitFee) {

        Parkinglot parkinglot = new Parkinglot();
        parkinglot.name = name;
        parkinglot.seoulDistrict = seoulDistrict;
        parkinglot.address = address != null ? address : "주소 미입력";
        parkinglot.baseFee = baseFee;
        parkinglot.unitFee = unitFee;
        parkinglot.totalSpots = totalSpots;
        parkinglot.usedSpots = 0;           // 초기화값.
        parkinglot.baseTime = 30;           // 30분.
        parkinglot.unitTime = 10;           // 10분 단위.
        return parkinglot;
    }

    public void updateParkinglot(ParkinglotUpdateRequest request) {
        this.name = request.getName();
        this.address = request.getAddress();
        this.seoulDistrict = request.getSeoulDistrict();
        this.totalSpots = request.getTotalSpots();
        this.baseFee = request.getBaseFee();
        this.unitFee = request.getUnitFee();
    }

    public void updateUsedSpots(boolean isUsing) {
        if (isUsing) {
            this.usedSpots++;
        } else {
            this.usedSpots--;
        }
    }
}
