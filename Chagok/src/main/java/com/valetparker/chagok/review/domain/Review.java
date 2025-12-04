package com.valetparker.chagok.review.domain;

import com.valetparker.chagok.parkinglot.domain.Parkinglot;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_review")
@Getter
@NoArgsConstructor
public class Review {

    /*
    * 엔티티 내 Command를 위한 함수들(CUD)은 valid 체크 어노테이션을
    * 함수 인자에 넣어둬야 함.
    * */

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reviewId;
    @Column(nullable = false)
    private Double rating;
    @Column(length = 1000)
    private String content;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime reviewCreatedAt;
    @Column
    @LastModifiedDate
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
