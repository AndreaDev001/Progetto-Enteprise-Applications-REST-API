package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService
{
    private final MessageDao messageDao;
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
    public void deleteMessage(Long messageID) {
        this.messageDao.findById(messageID).orElseThrow();
        this.messageDao.deleteById(messageID);
    }
}
