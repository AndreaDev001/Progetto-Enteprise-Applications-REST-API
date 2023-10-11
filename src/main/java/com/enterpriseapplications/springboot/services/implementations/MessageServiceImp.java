package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MessageServiceImp implements MessageService
{
    private final MessageDao messageDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Message,MessageDto> modelAssembler;
    private final PagedResourcesAssembler<Message> pagedResourcesAssembler;


    public MessageServiceImp(MessageDao messageDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Message> pagedResourcesAssembler) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Message.class,MessageDto.class,modelMapper);
    }

    @Override
    public MessageDto getMessage(UUID messageID) {
        Message message = this.messageDao.findById(messageID).orElseThrow();
        return this.modelMapper.map(message,MessageDto.class);
    }

    @Override
    public PagedModel<MessageDto> getMessages(Pageable pageable) {
        Page<Message> messages = this.messageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(messages,modelAssembler);
    }

    @Override
    public PagedModel<MessageDto> getSentMessages(UUID userID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getSentMessages(userID,pageable);
        return this.pagedResourcesAssembler.toModel(messages,modelAssembler);
    }

    @Override
    public PagedModel<MessageDto> getReceivedMessages(UUID userID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getReceivedMessages(userID,pageable);
        return this.pagedResourcesAssembler.toModel(messages,modelAssembler);
    }

    @Override
    public PagedModel<MessageDto> getMessagesBetween(UUID senderID, UUID receiverID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getMessagesBetween(senderID,receiverID,pageable);
        return this.pagedResourcesAssembler.toModel(messages,modelAssembler);
    }

    @Override
    @Transactional
    public MessageDto createMessage(CreateMessageDto createMessageDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User receiver = this.userDao.findById(createMessageDto.getReceiverID()).orElseThrow();
        if(requiredUser.getId().equals(receiver.getId()))
            throw new InvalidFormat("errors.message.invalidSender");
        Message message = new Message();
        message.setSender(requiredUser);
        message.setReceiver(receiver);
        message.setText(createMessageDto.getText());
        this.messageDao.save(message);
        return this.modelMapper.map(message,MessageDto.class);
    }

    @Override
    @Transactional
    public void deleteMessage(UUID messageID) {
        this.messageDao.findById(messageID).orElseThrow();
        this.messageDao.deleteById(messageID);
    }
}
