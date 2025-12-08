package com.valetparker.reviewservice.command.repository;

import com.valetparker.reviewservice.common.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaReviewCommandRepository extends JpaRepository<Review, Long> {

    Review save(Review review);

    Optional<Review> findById(Long reviewId);

    void deleteById(Long reviewId);
}
