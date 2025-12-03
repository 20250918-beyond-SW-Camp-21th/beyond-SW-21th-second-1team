package com.valetparker.chagok.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_review")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long reviewId;
    @Column(nullable = false)
    private double rating;
    @Column(length = 1000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime reviewCreatedAt;
    @Column
    private LocalDateTime reviewModifiedAt;
    @Column(nullable = false)
    private long writerId;
    @Column(nullable = false)
    private long parkinglotId;
    @Column(nullable = false)
    private long reservationId;
}
