package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ReviewService {
    PagedModel<ReviewDto> getReviews(Pageable pageable);
    PagedModel<ReviewDto> findAllWrittenReviews(UUID writerID, Pageable pageable);
    PagedModel<ReviewDto> findAllReceivedReviews(UUID receiverID,Pageable pageable);
    ReviewDto findReview(UUID writerID, UUID receiverID);
    ReviewDto createReview(CreateReviewDto createReviewDto);
    ReviewDto updateReview(UpdateReviewDto updateReviewDto);
    void deleteReview(UUID reviewID);
}
