package com.valetparker.reviewservice.common.entity;

import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
 * ====== 논의 사항 ======
 * client에 대해 엔티티는 어떻게 구성되어야 하는지?
 * ===========================
 * */
@Entity
@Table(name = "tbl_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime reviewCreatedAt;
    @Column
    @LastModifiedDate
    private LocalDateTime reviewModifiedAt;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "writer_id", nullable = false)
//    private User user;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parkinglot_id", nullable = false)
//    private ParkingLot parkinglot;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reservation_id", nullable = false)
//    private Reservation reservation;

    public void updateReview(ReviewUpdateRequest request) {
        this.content = request.getContent();
        this.rating = request.getRating();
    }

    public static Review create(
            Double rating,
            String content
//            User user,
//            ParkingLot parkingLot,
//            Reservation reservation
    ) {
        Review review = new Review();   // 기본 생성자 (PROTECTED) 사용
        review.rating = rating;
        review.content = content;
//        review.user = user;
//        review.parkinglot = parkingLot;
//        review.reservation = reservation;
        return review;
    }
}