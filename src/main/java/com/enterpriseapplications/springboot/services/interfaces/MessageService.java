package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface MessageService {
    PagedModel<MessageDto> getSentMessages(UUID userID, Pageable pageable);
    PagedModel<MessageDto> getReceivedMessages(UUID userID,Pageable pageable);
    PagedModel<MessageDto> getMessagesBetween(UUID senderID,UUID receiverID,Pageable pageable);
    MessageDto createMessage(CreateMessageDto createMessageDto);
    void deleteMessage(UUID messageID);
}
