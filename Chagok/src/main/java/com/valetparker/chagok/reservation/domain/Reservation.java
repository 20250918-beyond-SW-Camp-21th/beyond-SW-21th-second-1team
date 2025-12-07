package com.valetparker.chagok.reservation.domain;

import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.parkinglot.domain.Parkinglot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "tbl_reservation")
@NoArgsConstructor
@AllArgsConstructor
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

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long userNo;
    private Long parkinglotId;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkinglotId")
    private Parkinglot parkinglot;*/

}
