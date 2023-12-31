package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<MessageDto>> getMessages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getMessages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/private/{messageID}")
    @PreAuthorize("@permissionHandler.hasAccess(@messageDao,#messageID)")
    public ResponseEntity<MessageDto> getMessage(@PathVariable("messageID") UUID messageID) {
        return ResponseEntity.ok(this.messageService.getMessage(messageID));
    }

    @GetMapping("/private/{userID}/sent")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<MessageDto>> getSentMessages(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getSentMessages(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/private/{userID}/received")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<MessageDto>> getReceivedMessages(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getReceivedMessages(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/private/conversation/{conversationID}")
    @PreAuthorize("@permissionHandler.hasAccessMulti(@conversationDao,#conversationID)")
    public ResponseEntity<PagedModel<MessageDto>> getMessages(@PathVariable("conversationID") UUID conversationID,@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getMessages(conversationID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("private/conversation/{conversationID}/first")
    @PreAuthorize("@permissionHandler.hasAccessMulti(@conversationDao,#conversationID)")
    public ResponseEntity<MessageDto> getFirstMessage(@PathVariable("conversationID") UUID conversationID) {
        MessageDto messageDto = this.messageService.getFirstMessage(conversationID);
        return ResponseEntity.ok(messageDto);
    }

    @GetMapping("private/conversation/{conversationID}/last")
    @PreAuthorize("@permissionHandler.hasAccessMulti(@conversationDao,#conversationID)")
    public ResponseEntity<MessageDto> getLastMessage(@PathVariable("conversationID") UUID conversationID) {
        MessageDto messageDto = this.messageService.getLastMessage(conversationID);
        return ResponseEntity.ok(messageDto);
    }

    @GetMapping("private/{senderID}/{receiverID}/between")
    @PreAuthorize("@permissionHandler.hasAccess(#senderID) or @permissionHandler.hasAccess(#receiverID)")
    public ResponseEntity<PagedModel<MessageDto>> getMessagesBetween(@PathVariable("senderID") UUID senderID,@PathVariable("receiverID") UUID receiverID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<MessageDto> messages = this.messageService.getMessagesBetween(senderID,receiverID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<MessageDto> createMessage(@RequestBody @Valid CreateMessageDto createMessageDto) {
        return ResponseEntity.ok(this.messageService.createMessage(createMessageDto));
    }

    @DeleteMapping("/private/{messageID}")
    @PreAuthorize("@permissionHandler.hasAccess(@messageDao,#messageID)")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageID") UUID messageID) {
        this.messageService.deleteMessage(messageID);
        return ResponseEntity.noContent().build();
    }
}
