package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.services.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("{userID}/writer")
    public ResponseEntity<PaginationResponse<ReviewDto>> findAllWrittenReviews(@PathVariable("userID") Long userID, @Parameter @Valid PaginationRequest paginationRequest)
    {
        Page<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<ReviewDto>(reviews.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reviews.getTotalPages(),reviews.getTotalElements()));
    }

    @GetMapping("{userID}/received")
    public ResponseEntity<PaginationResponse<ReviewDto>> findAllReceivedReviews(@PathVariable("userID") Long userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReviewDto> reviews = this.reviewService.findAllWrittenReviews(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<ReviewDto>(reviews.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reviews.getTotalPages(),reviews.getTotalElements()));
    }

    @GetMapping("/review")
    public ResponseEntity<ReviewDto> findReview(@RequestParam("writerID") Long writerID,@RequestParam("receiverID") Long receiverID) {
        return ResponseEntity.ok(this.reviewService.findReview(writerID,receiverID));
    }
}
