package com.valetparker.chagok.using.domain;

import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.using.enums.UsingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tbl_using")
public class Using {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("BEFORE")
    private UsingStatus usingStatus;

    @Column(nullable = false)
    private int exceededCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;
}
