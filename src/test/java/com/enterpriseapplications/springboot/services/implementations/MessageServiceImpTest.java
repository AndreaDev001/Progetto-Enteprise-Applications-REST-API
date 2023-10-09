package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.entities.Message;
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
class MessageServiceImpTest extends GenericTestImp<Message,MessageDto> {

    private MessageServiceImp messageServiceImp;
    @Mock
    public MessageDao messageDao;
    @Mock
    public UserDao userDao;


    @Override
    protected void init() {
        super.init();
        messageServiceImp = new MessageServiceImp(messageDao,userDao,modelMapper,pagedResourcesAssembler);
        User sender = User.builder().id(UUID.randomUUID()).build();
        User receiver = User.builder().id(UUID.randomUUID()).build();
        firstElement = Message.builder().id(UUID.randomUUID()).sender(sender).receiver(receiver).text("text").build();
        secondElement = Message.builder().id(UUID.randomUUID()).sender(receiver).receiver(sender).text("text").build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    public boolean valid(Message message, MessageDto messageDto) {
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
        given(this.messageDao.findById(this.firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.messageDao.findById(this.secondElement.getId())).willReturn(Optional.of(secondElement));
        MessageDto firstMessage = this.messageServiceImp.getMessage(this.firstElement.getId());
        MessageDto secondMessage = this.messageServiceImp.getMessage(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstMessage));
        Assert.assertTrue(valid(this.secondElement,secondMessage));
    }

    @Test
    void getMessages() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.messageDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageDto> messages = this.messageServiceImp.getMessages(pageRequest);
        Assert.assertTrue(compare(elements,messages.getContent().stream().toList()));
    }

    @Test
    void getSentMessages() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.messageDao.getSentMessages(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageDto> messages = this.messageServiceImp.getSentMessages(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,messages.getContent().stream().toList()));
    }

    @Test
    void getReceivedMessages() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.messageDao.getReceivedMessages(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageDto> messages = this.messageServiceImp.getReceivedMessages(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,messages.getContent().stream().toList()));
    }

    @Test
    void getMessagesBetween() {
        User sender = User.builder().id(UUID.randomUUID()).build();
        User receiver = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.messageDao.getMessagesBetween(sender.getId(),receiver.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageDto> messages = this.messageServiceImp.getMessagesBetween(sender.getId(),receiver.getId(),pageRequest);
        Assert.assertTrue(compare(elements,messages.getContent().stream().toList()));
    }

    @Test
    void createMessage() {
    }
}