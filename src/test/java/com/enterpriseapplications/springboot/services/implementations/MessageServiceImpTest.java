package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.entities.Message;
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
class MessageServiceImpTest {

    private MessageServiceImp messageServiceImp;
    private Message firstMessage;
    private Message secondMessage;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Message> pagedResourcesAssembler;

    @Mock
    public MessageDao messageDao;
    @Mock
    public UserDao userDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        messageServiceImp = new MessageServiceImp(messageDao,userDao,modelMapper,pagedResourcesAssembler);
        User sender = User.builder().id(UUID.randomUUID()).build();
        User receiver = User.builder().id(UUID.randomUUID()).build();
        firstMessage = Message.builder().id(UUID.randomUUID()).sender(sender).receiver(receiver).text("text").build();
        secondMessage = Message.builder().id(UUID.randomUUID()).sender(receiver).receiver(sender).text("text").build();
    }

    boolean valid(Message message, MessageDto messageDto) {
        Assert.assertNotNull(messageDto);
        Assert.assertEquals(message.getId(),messageDto.getId());
        Assert.assertEquals(message.getText(),messageDto.getText());
        Assert.assertEquals(message.getSender().getId(),messageDto.getSender().getId());
        Assert.assertEquals(message.getReceiver().getId(),messageDto.getReceiver().getId());
        Assert.assertEquals(message.getCreatedDate(),messageDto.getCreatedDate());
        return true;
    }

    @Test
    void getMessage() {
        given(this.messageDao.findById(this.firstMessage.getId())).willReturn(Optional.of(firstMessage));
        given(this.messageDao.findById(this.secondMessage.getId())).willReturn(Optional.of(secondMessage));
        MessageDto firstMessage = this.messageServiceImp.getMessage(this.firstMessage.getId());
        MessageDto secondMessage = this.messageServiceImp.getMessage(this.secondMessage.getId());
        Assert.assertTrue(valid(this.firstMessage,firstMessage));
        Assert.assertTrue(valid(this.secondMessage,secondMessage));
    }

    @Test
    void getMessages() {
    }

    @Test
    void getSentMessages() {
    }

    @Test
    void getReceivedMessages() {
    }

    @Test
    void getMessagesBetween() {
    }

    @Test
    void createMessage() {
    }
}