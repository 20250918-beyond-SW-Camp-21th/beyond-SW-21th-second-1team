package com.valetparker.chagok.review.service;

import com.valetparker.chagok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
//    private final ModelMapper modelMapper;
}
