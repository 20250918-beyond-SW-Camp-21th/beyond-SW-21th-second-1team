package com.valetparker.reservationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ReservationServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void basicTest() {
        assertThat(1 + 1).isEqualTo(2);
    }

}
