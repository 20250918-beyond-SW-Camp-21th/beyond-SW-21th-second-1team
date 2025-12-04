package com.valetparker.chagok.reservation.domain;

import com.valetparker.chagok.user.domain.User;
import com.valetparker.chagok.parkinglot.domain.Parkinglot;
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
    private Long reservationId;

    @Column(nullable = false)
    private String partnerOrderId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isCanceled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkinglotId")
    private Parkinglot parkinglot;

}
