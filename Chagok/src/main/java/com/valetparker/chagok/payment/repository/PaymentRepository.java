package com.valetparker.chagok.payment.repository;

import com.valetparker.chagok.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPartnerOrderId(String partnerOrderId);
}