package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReviewDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReviewServiceImpTest extends GenericTestImp<Review,ReviewDto> {

    private ReviewServiceImp reviewServiceImp;
    @Mock
    public ReviewDao reviewDao;
    @Mock
    public UserDao userDao;


    @Override
    protected void init() {
        super.init();
        reviewServiceImp = new ReviewServiceImp(reviewDao,userDao,modelMapper,pagedResourcesAssembler);
        User writer = User.builder().id(UUID.randomUUID()).username("andrea").email("marchioandrea02@gmail.com").description("Description").name("andrea").surname("Marchio").build();
        User receiver = User.builder().id(UUID.randomUUID()).username("andrea1").email("andrea@gmail.com").description("Description").name("andrea").surname("Marchio").build();
        firstElement = Review.builder().text("test").rating(10).writer(writer).receiver(receiver).build();
        secondElement = Review.builder().text("text").rating(5).writer(receiver).receiver(writer).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before()
    {
        init();
        this.defaultBefore();
    }

    @Test
    void getReview() {
        given(this.reviewDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.reviewDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        ReviewDto firstReviewDto = this.reviewServiceImp.getReview(firstElement.getId());
        ReviewDto secondReviewDto = this.reviewServiceImp.getReview(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstReviewDto));
        Assert.assertTrue(valid(secondElement,secondReviewDto));
    }

    @Override
    public boolean valid(Review review, ReviewDto reviewDto) {
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
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reviewDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReviewDto> pagedModel = this.reviewServiceImp.getReviews(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void findAllWrittenReviews() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reviewDao.findAllWrittenReviews(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReviewDto> pagedModel = this.reviewServiceImp.findAllWrittenReviews(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }


    @Test
    void findAllReceivedReviews() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reviewDao.findAllReviewsReceived(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReviewDto> pagedModel = this.reviewServiceImp.findAllReceivedReviews(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void findReview() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        given(this.reviewDao.findReview(firstUser.getId(),secondUser.getId())).willReturn(Optional.of(firstElement));
        given(this.reviewDao.findReview(secondUser.getId(),firstUser.getId())).willReturn(Optional.of(secondElement));
        ReviewDto firstReview = this.reviewServiceImp.findReview(firstUser.getId(),secondUser.getId());
        ReviewDto secondReview = this.reviewServiceImp.findReview(secondUser.getId(),firstUser.getId());
        Assert.assertTrue(valid(firstElement,firstReview));
        Assert.assertTrue(valid(secondElement,secondReview));
    }

    @Test
    void createReview() {
        User user = User.builder().id(UUID.randomUUID()).build();
        CreateReviewDto createReviewDto = CreateReviewDto.builder().reviewedID(user.getId()).rating(10).text("TEXT").build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.userDao.findById(user.getId())).willReturn(Optional.of(user));
        given(this.reviewDao.getReviews(user.getId())).willReturn(elements);
        given(this.reviewDao.save(any(Review.class))).willReturn(firstElement);
        ReviewDto reviewDto = this.reviewServiceImp.createReview(createReviewDto);
        Assert.assertTrue(valid(firstElement,reviewDto));
    }

    @Test
    void updateReview() {
    }
}