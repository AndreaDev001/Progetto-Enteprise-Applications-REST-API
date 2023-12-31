package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface MessageService {
    MessageDto getMessage(UUID messageID);
    PagedModel<MessageDto> getMessages(Pageable pageable);
    PagedModel<MessageDto> getSentMessages(UUID userID, Pageable pageable);
    PagedModel<MessageDto> getReceivedMessages(UUID userID,Pageable pageable);
    PagedModel<MessageDto> getMessagesBetween(UUID senderID,UUID receiverID,Pageable pageable);
    PagedModel<MessageDto> getMessages(UUID conversationID,Pageable pageable);
    MessageDto getLastMessage(UUID conversationID);
    MessageDto getFirstMessage(UUID conversationID);
    MessageDto createMessage(CreateMessageDto createMessageDto);
    void deleteMessage(UUID messageID);
}
