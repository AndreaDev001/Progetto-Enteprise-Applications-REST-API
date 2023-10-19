package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface ConversationService
{
    PagedModel<ConversationDto> getConversations(Pageable pageable);
    ConversationDto find(UUID id);
    List<ConversationDto> getConversationByFirst(UUID id);
    List<ConversationDto> getConversationBySecond(UUID id);
    List<ConversationDto> getConversation(UUID first,UUID second);
    List<ConversationDto> getConversations(UUID userID);
    PagedModel<ConversationDto> getConversationByProduct(UUID productID, Pageable pageable);
    ConversationDto createConversation(CreateConversationDto createConversationDto);
    void deleteConversation(UUID id);
}
