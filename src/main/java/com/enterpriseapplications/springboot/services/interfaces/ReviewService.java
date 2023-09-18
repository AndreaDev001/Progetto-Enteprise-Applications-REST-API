package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewService {
    Page<ReviewDto> findAllWrittenReviews(Long writerID, Pageable pageable);
    Page<ReviewDto> findAllReceivedReviews(Long receiverID,Pageable pageable);
    ReviewDto findReview(Long writerID, Long receiverID);
    ReviewDto createReview(CreateReviewDto createReviewDto);
    void deleteReview(Long reviewID);
}
