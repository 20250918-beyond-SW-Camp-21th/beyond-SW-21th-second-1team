package com.valetparker.reservationservice.common.entity;

import com.valetparker.reservationservice.common.converter.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

//    @Column(nullable = true)
//    private String partnerOrderId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isCanceled;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long userNo;
    private Long parkinglotId;

    public static Reservation create(
//            String PartnerOrderId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Boolean isCanceled,
            LocalDateTime createdAt,
            Long userNo,
            Long parkinglotId
    ) {
        Reservation reservation = new Reservation();
//        reservation.partnerOrderId = PartnerOrderId;
        reservation.startTime = startTime;
        reservation.endTime = endTime;
        reservation.isCanceled = isCanceled;
        reservation.createdAt = createdAt;
        reservation.userNo = userNo;
        reservation.parkinglotId = parkinglotId;
        return reservation;
    }

    // 취소
    public void cancel() {
        isCanceled = true;
    }
}
