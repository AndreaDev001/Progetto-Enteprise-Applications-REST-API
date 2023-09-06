package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {
    Page<Review> findAllWrittenReviews(Long writerID, Pageable pageable);
    Page<Review> findAllReceivedReviews(Long receiverID,Pageable pageable);
    Optional<Review> findReview(Long writerID, Long receiverID);
    void deleteReview(Long reviewID);
}
