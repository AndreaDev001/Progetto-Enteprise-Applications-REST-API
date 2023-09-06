package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.services.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ReviewDao reviewDao;

    @Override
    public Page<Review> findAllWrittenReviews(Long writerID, Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllWrittenReviews(writerID,pageable);
        return new PageImpl<>(reviews.toList(),pageable,reviews.getTotalElements());
    }
    @Override
    public Page<Review> findAllReceivedReviews(Long receiverID,Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllReviewsReceived(receiverID,pageable);
        return new PageImpl<>(reviews.toList(),pageable,reviews.getTotalElements());
    }

    @Override
    public Optional<Review> findReview(Long writerID, Long receiverID) {
        return this.reviewDao.findReview(writerID,receiverID);
    }

    @Override
    public void deleteReview(Long reviewID) {
        this.reviewDao.delete(reviewID);
    }
}
