package com.valetparker.chagok.using.domain;

import com.valetparker.chagok.using.enums.UsingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tbl_using")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private Long reservationId;

    public UsingStatus getUsingStatus() {
        return this.usingStatus;
    }

    public long getUsingId() {
        return this.usingId;
    }

    public int getExceededCount() {
        return this.exceededCount;
    }

    public void setUsingStatus(UsingStatus usingStatus) {
        this.usingStatus = usingStatus;
    }

    public void setExceededCount(int exceededCount) {
        this.exceededCount = exceededCount;
    }

}
