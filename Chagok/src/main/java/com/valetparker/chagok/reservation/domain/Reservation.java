package com.valetparker.chagok.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbl_reservation")
public class Reservation {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isCanceled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private long userNo;

    @Column(nullable = false)
    private long parkinglotId;

}
