package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<MessageDto> getSentMessages(Long userID, Pageable pageable);
    Page<MessageDto> getReceivedMessages(Long userID,Pageable pageable);
    Page<MessageDto> getMessagesBetween(Long senderID,Long receiverID,Pageable pageable);
    MessageDto createMessage(CreateMessageDto createMessageDto);
    void deleteMessage(Long messageID);
}
