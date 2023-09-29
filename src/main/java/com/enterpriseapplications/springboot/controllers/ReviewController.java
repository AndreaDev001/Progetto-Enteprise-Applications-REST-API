package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.services.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<PagedModel<ReviewDto>> getAll(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReviewDto> reviews = this.reviewService.getReviews(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("{userID}/writer")
    public ResponseEntity<PagedModel<ReviewDto>> findAllWrittenReviews(@PathVariable("userID") UUID userID, @Parameter @Valid PaginationRequest paginationRequest)
    {
        PagedModel<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("{userID}/received")
    public ResponseEntity<PagedModel<ReviewDto>> findAllReceivedReviews(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/review")
    public ResponseEntity<ReviewDto> findReview(@RequestParam("writerID") UUID writerID,@RequestParam("receiverID") UUID receiverID) {
        return ResponseEntity.ok(this.reviewService.findReview(writerID,receiverID));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid CreateReviewDto createReviewDto) {
        return ResponseEntity.ok(this.reviewService.createReview(createReviewDto));
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(@RequestBody @Valid UpdateReviewDto updateReviewDto) {
        return ResponseEntity.ok(this.reviewService.updateReview(updateReviewDto));
    }

    @DeleteMapping("{reviewID}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewID") UUID reviewID) {
        this.reviewService.deleteReview(reviewID);
        return ResponseEntity.noContent().build();
    }
}
