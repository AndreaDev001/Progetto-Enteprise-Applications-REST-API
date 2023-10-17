package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ReplyDao;
import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.data.entities.Reply;
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

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReplyServiceImpTest extends GenericTestImp<Reply,ReplyDto> {

    private ReplyServiceImp replyServiceImp;

    @Mock
    private ReplyDao replyDao;
    @Mock
    private ReviewDao reviewDao;
    @Mock
    private UserDao userDao;


    @Override
    protected void init() {
        super.init();
        this.replyServiceImp = new ReplyServiceImp(replyDao,reviewDao,userDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Review firstReview = Review.builder().id(UUID.randomUUID()).build();
        Review secondReview = Review.builder().id(UUID.randomUUID()).build();
        firstElement = Reply.builder().id(UUID.randomUUID()).text("text").writer(firstUser).review(firstReview).build();
        secondElement = Reply.builder().id(UUID.randomUUID()).text("text").writer(secondUser).review(secondReview).build();
        elements = List.of(firstElement,secondElement);

    }

    @BeforeEach
    public void before() {
        init();
    }
    public boolean valid(Reply reply, ReplyDto replyDto) {
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
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.replyDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReplyDto> replies = this.replyServiceImp.getReplies(pageRequest);
        Assert.assertTrue(compare(elements,replies.getContent().stream().toList()));
        Assert.assertTrue(validPage(replies,20,0,1,2));
    }

    @Test
    void getReply() {
        given(this.replyDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.replyDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        ReplyDto firstReplyDto = this.replyServiceImp.getReply(firstElement.getId());
        ReplyDto secondReplyDto = this.replyServiceImp.getReply(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstReplyDto));
        Assert.assertTrue(valid(secondElement,secondReplyDto));
    }

    @Test
    void createReply() {
    }

    @Test
    void updateReply() {
    }

    @Test
    void getWrittenReplies() {
        PageRequest pageRequest = PageRequest.of(0,20);
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.replyDao.findByWriter(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReplyDto> pagedModel = this.replyServiceImp.getWrittenReplies(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }
}