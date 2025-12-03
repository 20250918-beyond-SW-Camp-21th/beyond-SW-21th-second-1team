package com.valetparker.chagok.using.dto;

import com.valetparker.chagok.using.enums.UsingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsingDto {

    private long usingId;
    private UsingStatus usingStatus;
    private int exceededCount;
    private long reservationId;
}
