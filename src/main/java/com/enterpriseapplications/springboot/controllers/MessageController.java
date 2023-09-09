package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("{userID}/sent")
    public ResponseEntity<PaginationResponse<MessageDto>> getSentMessages(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<MessageDto> messages = this.messageService.getSentMessages(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(messages.toList(),paginationRequest.getPage(),paginationRequest.getPage(),messages.getTotalPages(),messages.getTotalElements()));
    }

    @GetMapping("{userID}/received")
    public ResponseEntity<PaginationResponse<MessageDto>> getReceivedMessages(@PathVariable("userID") Long userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<MessageDto> messages = this.messageService.getReceivedMessages(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(messages.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),messages.getTotalPages(),messages.getTotalElements()));
    }

    @GetMapping("{senderID}/{receiverID}/between")
    public ResponseEntity<PaginationResponse<MessageDto>> getMessagesBetween(@PathVariable("senderID") Long senderID,@PathVariable("receiverID") Long receiverID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<MessageDto> messages = this.messageService.getMessagesBetween(senderID,receiverID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(messages.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),messages.getTotalPages(),messages.getTotalElements()));
    }
}
