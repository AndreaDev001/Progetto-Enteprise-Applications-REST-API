package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService
{
    private final MessageDao messageDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<MessageDto> getSentMessages(Long userID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getSentMessages(userID,pageable);
        return new PageImpl<>(messages.stream().map(message -> this.modelMapper.map(message, MessageDto.class)).collect(Collectors.toList()),pageable,messages.getTotalElements());
    }

    @Override
    public Page<MessageDto> getReceivedMessages(Long userID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getReceivedMessages(userID,pageable);
        return new PageImpl<>(messages.stream().map(review -> this.modelMapper.map(review,MessageDto.class)).collect(Collectors.toList()),pageable,messages.getTotalElements());
    }

    @Override
    public Page<MessageDto> getMessagesBetween(Long senderID, Long receiverID, Pageable pageable) {
        Page<Message> messages = this.messageDao.getMessagesBetween(senderID,receiverID,pageable);
        return new PageImpl<>(messages.stream().map(review -> this.modelMapper.map(review,MessageDto.class)).collect(Collectors.toList()),pageable,messages.getTotalElements());
    }

    @Override
    @Transactional
    public MessageDto createMessage(CreateMessageDto createMessageDto) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
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
    public void deleteMessage(Long messageID) {
        this.messageDao.findById(messageID).orElseThrow();
        this.messageDao.deleteById(messageID);
    }
}
