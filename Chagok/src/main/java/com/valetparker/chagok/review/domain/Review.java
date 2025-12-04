package com.valetparker.chagok.review.domain;

import com.valetparker.chagok.user.User;
import com.valetparker.chagok.parkinglot.domain.Parkinglot;
import com.valetparker.chagok.reservation.domain.Reservation;
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
    private Long reviewId;
    @Column(nullable = false)
    private Double rating;
    @Column(length = 1000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime reviewCreatedAt;
    @Column
    private LocalDateTime reviewModifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    @Column(nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkinglot_id")
    @Column(nullable = false)
    private Parkinglot parkinglot;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @Column(nullable = false)
    private Reservation reservation;
}
