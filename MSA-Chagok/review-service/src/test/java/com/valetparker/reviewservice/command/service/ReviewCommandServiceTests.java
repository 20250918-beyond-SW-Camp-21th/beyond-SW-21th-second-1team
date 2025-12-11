package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.client.ReservationClient;
import com.valetparker.reviewservice.command.repository.JpaReviewCommandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewCommandServiceTests {

    @Mock
    private JpaReviewCommandRepository repository;

    @Mock
    private ReservationClient reservationClient;

    @InjectMocks
    private ReviewCommandService service;

    @Test
    void createReview_Ok() {

    }

}