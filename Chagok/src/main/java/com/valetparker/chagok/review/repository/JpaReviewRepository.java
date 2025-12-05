package com.valetparker.chagok.review.repository;

import com.valetparker.chagok.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewRepository extends ReviewRepository, JpaRepository<Review, Long> {
}
