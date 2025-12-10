package paymentservice.using.repository;

import com.valetparker.chagok.using.domain.Using;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsingRepository extends JpaRepository<Using, Long> {

    // 예약 ID로 Using 조회 (1:1 관계 가정)
    Optional<Using> findByReservationId(Long reservationId);

    Using findByUsingId(Long usingId);
}
