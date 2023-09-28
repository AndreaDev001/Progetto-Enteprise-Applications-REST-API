package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface ReviewService {
    PagedModel<ReviewDto> findAllWrittenReviews(Long writerID, Pageable pageable);
    PagedModel<ReviewDto> findAllReceivedReviews(Long receiverID,Pageable pageable);
    ReviewDto findReview(Long writerID, Long receiverID);
    ReviewDto createReview(CreateReviewDto createReviewDto);
    ReviewDto updateReview(UpdateReviewDto updateReviewDto);
    void deleteReview(Long reviewID);
}
