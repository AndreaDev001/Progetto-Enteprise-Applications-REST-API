package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface MessageService {
    PagedModel<MessageDto> getSentMessages(Long userID, Pageable pageable);
    PagedModel<MessageDto> getReceivedMessages(Long userID,Pageable pageable);
    PagedModel<MessageDto> getMessagesBetween(Long senderID,Long receiverID,Pageable pageable);
    MessageDto createMessage(CreateMessageDto createMessageDto);
    void deleteMessage(Long messageID);
}
