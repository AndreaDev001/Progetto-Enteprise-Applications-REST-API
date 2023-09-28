package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.stream.Collectors;


@Service
public class ReviewServiceImp implements ReviewService {

    private final ReviewDao reviewDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Review,ReviewDto> modelAssembler;
    private final PagedResourcesAssembler<Review> pagedResourcesAssembler;

    public ReviewServiceImp(ReviewDao reviewDao, UserDao userDao, ModelMapper modelMapper,PagedResourcesAssembler<Review> pagedResourcesAssembler) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Review.class,ReviewDto.class,modelMapper);
    }

    @Override
    public PagedModel<ReviewDto> findAllWrittenReviews(Long writerID, Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllWrittenReviews(writerID,pageable);
        return this.pagedResourcesAssembler.toModel(reviews,modelAssembler);
    }
    @Override
    public PagedModel<ReviewDto> findAllReceivedReviews(Long receiverID,Pageable pageable) {
        Page<Review> reviews = this.reviewDao.findAllReviewsReceived(receiverID,pageable);
        return this.pagedResourcesAssembler.toModel(reviews,modelAssembler);
    }

    @Override
    public ReviewDto findReview(Long writerID, Long receiverID) {
        return this.modelMapper.map(this.reviewDao.findReview(writerID,receiverID).orElseThrow(),ReviewDto.class);
    }

    @Override
    @Transactional
    public ReviewDto createReview(CreateReviewDto createReviewDto) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User reviewed = this.userDao.findById(createReviewDto.getReviewedID()).orElseThrow();
        if(requiredUser.getId().equals(reviewed.getId()))
            throw new InvalidFormat("errors.reviews.invalidReviewer");
        Review review = new Review();
        review.setWriter(requiredUser);
        review.setReceiver(reviewed);
        review.setText(createReviewDto.getText());
        review.setRating(createReviewDto.getRating());
        this.reviewDao.save(review);
        return this.modelMapper.map(review,ReviewDto.class);
    }

    @Override
    @Transactional
    public ReviewDto updateReview(UpdateReviewDto updateReviewDto) {
        Review requiredReview = this.reviewDao.findById(updateReviewDto.getReviewID()).orElseThrow();
        if(updateReviewDto.getRating() != null)
            requiredReview.setRating(updateReviewDto.getRating());
        if(updateReviewDto.getText() != null)
            requiredReview.setText(updateReviewDto.getText());
        this.reviewDao.save(requiredReview);
        return this.modelMapper.map(requiredReview,ReviewDto.class);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewID) {
        this.reviewDao.findById(reviewID).orElseThrow();
        this.reviewDao.deleteById(reviewID);
    }
}
