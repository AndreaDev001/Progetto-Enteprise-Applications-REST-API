package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class MessageController {
    private final MessageService messageService;


    @GetMapping
    public ResponseEntity<PagedModel<MessageDto>> getMessages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getMessages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("{userID}/sent")
    public ResponseEntity<PagedModel<MessageDto>> getSentMessages(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getSentMessages(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("{userID}/received")
    public ResponseEntity<PagedModel<MessageDto>> getReceivedMessages(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getReceivedMessages(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("{senderID}/{receiverID}/between")
    public ResponseEntity<PagedModel<MessageDto>> getMessagesBetween(@PathVariable("senderID") UUID senderID,@PathVariable("receiverID") UUID receiverID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getMessagesBetween(senderID,receiverID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody @Valid CreateMessageDto createMessageDto) {
        return ResponseEntity.ok(this.messageService.createMessage(createMessageDto));
    }

    @DeleteMapping("{messageID}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageID") UUID messageID) {
        this.messageService.deleteMessage(messageID);
        return ResponseEntity.noContent().build();
    }
}
