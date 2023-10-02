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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReviewDto>> getAll(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReviewDto> reviews = this.reviewService.getReviews(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/public/{reviewID}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("reviewID") UUID reviewID) {
        return ResponseEntity.ok(this.reviewService.getReview(reviewID));
    }

    @GetMapping("/public/{userID}/writer")
    public ResponseEntity<PagedModel<ReviewDto>> findAllWrittenReviews(@PathVariable("userID") UUID userID, @Parameter @Valid PaginationRequest paginationRequest)
    {
        PagedModel<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/public/{userID}/received")
    public ResponseEntity<PagedModel<ReviewDto>> findAllReceivedReviews(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/public/review")
    public ResponseEntity<ReviewDto> findReview(@RequestParam("writerID") UUID writerID,@RequestParam("receiverID") UUID receiverID) {
        return ResponseEntity.ok(this.reviewService.findReview(writerID,receiverID));
    }

    @PostMapping("/private")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid CreateReviewDto createReviewDto) {
        return ResponseEntity.ok(this.reviewService.createReview(createReviewDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@reviewDao,#updateReviewDto.reviewID)")
    public ResponseEntity<ReviewDto> updateReview(@RequestBody @Valid UpdateReviewDto updateReviewDto) {
        return ResponseEntity.ok(this.reviewService.updateReview(updateReviewDto));
    }

    @DeleteMapping("/private/{reviewID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reviewDao,#reviewID)")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewID") UUID reviewID) {
        this.reviewService.deleteReview(reviewID);
        return ResponseEntity.noContent().build();
    }
}
