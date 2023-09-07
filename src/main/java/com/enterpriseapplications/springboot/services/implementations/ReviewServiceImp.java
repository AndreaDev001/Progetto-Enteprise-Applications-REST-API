package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.services.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ReviewDao reviewDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<ReviewDto> findAllWrittenReviews(Long writerID, Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllWrittenReviews(writerID,pageable);
        return new PageImpl<>(reviews.stream().map(review -> this.modelMapper.map(review,ReviewDto.class)).collect(Collectors.toList()),pageable,reviews.getTotalElements());
    }
    @Override
    public Page<ReviewDto> findAllReceivedReviews(Long receiverID,Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllReviewsReceived(receiverID,pageable);
        return new PageImpl<>(reviews.stream().map(review -> this.modelMapper.map(review,ReviewDto.class)).collect(Collectors.toList()),pageable,reviews.getTotalElements());
    }

    @Override
    public ReviewDto findReview(Long writerID, Long receiverID) {
        return this.modelMapper.map(this.reviewDao.findReview(writerID,receiverID).orElseThrow(),ReviewDto.class);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewID) {
        this.reviewDao.deleteById(reviewID);
    }
}
