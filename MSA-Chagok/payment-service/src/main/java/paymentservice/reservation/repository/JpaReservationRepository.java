package paymentservice.reservation.repository;

import com.valetparker.chagok.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends ReservationRepository, JpaRepository<Reservation, Long> {
}