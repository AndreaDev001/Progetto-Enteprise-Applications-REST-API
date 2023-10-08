package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.data.entities.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.ui.ModelMap;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReviewServiceImpTest {

    private ReviewServiceImp reviewServiceImp;
    private Review firstReview;
    private Review secondReview;
    private PagedResourcesAssembler<Review> pagedResourcesAssembler;
    private ModelMapper modelMapper;
    @Mock
    public ReviewDao reviewDao;
    @Mock
    public UserDao userDao;


    @BeforeEach
    public void before()
    {
        modelMapper = new ModelMapper();
        reviewServiceImp = new ReviewServiceImp(reviewDao,userDao,modelMapper,pagedResourcesAssembler);
        User writer = User.builder().id(UUID.randomUUID()).username("andrea").email("marchioandrea02@gmail.com").description("Description").name("andrea").surname("Marchio").build();
        User receiver = User.builder().id(UUID.randomUUID()).username("andrea1").email("andrea@gmail.com").description("Description").name("andrea").surname("Marchio").build();
        firstReview = Review.builder().text("test").rating(10).writer(writer).receiver(receiver).build();
        secondReview = Review.builder().text("text").rating(5).writer(receiver).receiver(writer).build();
    }

    @Test
    void getReview() {
        given(this.reviewDao.findById(firstReview.getId())).willReturn(Optional.of(firstReview));
        given(this.reviewDao.findById(secondReview.getId())).willReturn(Optional.of(secondReview));
        ReviewDto firstReviewDto = this.reviewServiceImp.getReview(firstReview.getId());
        ReviewDto secondReviewDto = this.reviewServiceImp.getReview(secondReview.getId());
        Assert.assertTrue(valid(firstReview,firstReviewDto));
        Assert.assertTrue(valid(secondReview,secondReviewDto));
    }


    boolean valid(Review review, ReviewDto reviewDto) {
        Assert.assertNotNull(reviewDto);
        Assert.assertEquals(review.getId(),reviewDto.getId());
        Assert.assertEquals(review.getText(),reviewDto.getText());
        Assert.assertEquals(review.getRating(),reviewDto.getRating());
        Assert.assertEquals(review.getWriter().getId(),reviewDto.getWriter().getId());
        Assert.assertEquals(review.getReceiver().getId(),reviewDto.getReceiver().getId());
        Assert.assertEquals(review.getCreatedDate(),reviewDto.getCreatedDate());
        return true;
    }

    @Test
    void getReviews() {
    }

    @Test
    void findAllWrittenReviews() {
    }

    @Test
    void findAllReceivedReviews() {
    }

    @Test
    void findReview() {
    }

    @Test
    void createReview() {
    }

    @Test
    void updateReview() {
    }
}