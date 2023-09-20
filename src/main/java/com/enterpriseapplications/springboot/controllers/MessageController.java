package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.MessageDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
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

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody @Valid CreateMessageDto createMessageDto) {
        return ResponseEntity.ok(this.messageService.createMessage(createMessageDto));
    }

    @DeleteMapping("{messageID}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageID") Long messageID) {
        this.messageService.deleteMessage(messageID);
        return ResponseEntity.noContent().build();
    }
}
