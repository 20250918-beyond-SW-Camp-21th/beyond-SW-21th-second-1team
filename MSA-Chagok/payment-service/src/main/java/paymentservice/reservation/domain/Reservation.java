package paymentservice.reservation.domain;

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

    @Column(nullable = true)
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

    // 취소
    public void cancel() {
        isCanceled = true;
    }

    // 주문번호 생성
    public void assignPartnerOrderId() {
        this.partnerOrderId = String.format("ORDER_%03d", this.reservationId);
    }

}
