package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReplyDao;
import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.data.entities.Reply;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReplyServiceImpTest {

    private Reply firstReply;
    private Reply secondReply;
    private ReplyServiceImp replyServiceImp;
    private PagedResourcesAssembler<Reply> pagedResourcesAssembler;
    private ModelMapper modelMapper;

    @Mock
    private ReplyDao replyDao;
    @Mock
    private ReviewDao reviewDao;
    @Mock
    private UserDao userDao;


    @BeforeEach
    public void before() {
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        modelMapper = new ModelMapper();
        this.replyServiceImp = new ReplyServiceImp(replyDao,reviewDao,userDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Review firstReview = Review.builder().id(UUID.randomUUID()).build();
        Review secondReview = Review.builder().id(UUID.randomUUID()).build();
        firstReply = Reply.builder().id(UUID.randomUUID()).text("text").writer(firstUser).review(firstReview).build();
        secondReply = Reply.builder().id(UUID.randomUUID()).text("text").writer(secondUser).review(secondReview).build();
    }
    boolean valid(Reply reply, ReplyDto replyDto) {
        Assert.assertNotNull(replyDto);
        Assert.assertEquals(reply.getId(),replyDto.getId());
        Assert.assertEquals(reply.getText(),replyDto.getText());
        Assert.assertEquals(reply.getWriter().getId(),replyDto.getWriter().getId());
        Assert.assertEquals(reply.getReview().getId(),replyDto.getReviewID());
        Assert.assertEquals(reply.getCreatedDate(),replyDto.getCreatedDate());
        return true;
    }

    @Test
    void getReplyByReview() {
    }

    @Test
    void getReplies() {
    }

    @Test
    void getReply() {
        given(this.replyDao.findById(firstReply.getId())).willReturn(Optional.of(firstReply));
        given(this.replyDao.findById(secondReply.getId())).willReturn(Optional.of(secondReply));
        ReplyDto firstReplyDto = this.replyServiceImp.getReply(firstReply.getId());
        ReplyDto secondReplyDto = this.replyServiceImp.getReply(secondReply.getId());
        Assert.assertTrue(valid(firstReply,firstReplyDto));
        Assert.assertTrue(valid(secondReply,secondReplyDto));
    }

    @Test
    void createReply() {
    }

    @Test
    void updateReply() {
    }

    @Test
    void getWrittenReplies() {
    }
}