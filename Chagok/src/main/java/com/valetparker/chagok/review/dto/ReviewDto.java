package com.valetparker.chagok.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/*
 * ====== 논의 사항 ======
 * 1. 다른분들 Dto 완성할 때까지 Dto 주석처리..
 * ===========================
 * */
@Getter
@Setter
public class ReviewDto {

    private Long reviewId;
    private Double rating;
    private String content;
    private LocalDateTime reviewCreatedAt;
    private LocalDateTime reviewModifiedAt;
//    private UserDto user;
//    private ParkinglotDto parkinglot;
//    private ReservationDto reservation;

}
