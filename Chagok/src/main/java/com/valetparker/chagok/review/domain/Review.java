package com.valetparker.chagok.review.domain;

//import com.valetparker.chagok.parkinglot.domain.Parkinglot;
import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.review.dto.request.ReviewUpdateRequest;
//import com.valetparker.chagok.user.domain.User;
import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.using.domain.Using;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "tbl_review")
//@Getter
//@NoArgsConstructor
@Entity
@Table(name = "tbl_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
//@Builder
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
    @JoinColumn(name = "writer_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkinglot_id", nullable = false)
    private ParkingLot parkinglot;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "using_id", nullable = false)
//    private Using using;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public void updateReview(ReviewUpdateRequest request) {
        this.content = request.getContent();
        this.rating = request.getRating();
    }

    public static Review create(
            Double rating,
            String content,
            User user,
            ParkingLot parkingLot,
            Reservation reservation
    ) {
        Review review = new Review();   // 기본 생성자 (PROTECTED) 사용
        review.rating = rating;
        review.content = content;
        review.user = user;
        review.parkinglot = parkingLot;
        review.reservation = reservation;
        return review;
    }
}