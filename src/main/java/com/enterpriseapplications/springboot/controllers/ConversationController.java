package com.enterpriseapplications.springboot.controllers;

import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import com.enterpriseapplications.springboot.services.interfaces.ConversationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ConversationController
{
    private final ConversationService conversationService;

    @GetMapping("/public")
    public ResponseEntity<PagedModel<ConversationDto>> getConversations(@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ConversationDto> conversations = this.conversationService.getConversations(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/private/{conversationID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<ConversationDto> getConversation(@PathVariable("conversationID") UUID conversation) {
        return ResponseEntity.ok(this.conversationService.find(conversation));
    }
    @GetMapping("/private/first/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<List<ConversationDto>> getConversationsByFirst(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.conversationService.getConversationByFirst(userID));
    }

    @GetMapping("/private/second/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<List<ConversationDto>> getConversationsBySecond(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.conversationService.getConversationBySecond(userID));
    }

    @GetMapping("/public/{userID}")
    public ResponseEntity<List<ConversationDto>> getConversations(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.conversationService.getConversations(userID));
    }

    @GetMapping("/private/product/{productID}")
    public ResponseEntity<PagedModel<ConversationDto>> getConversations(@PathVariable("productID") UUID productID,@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ConversationDto> conversations = this.conversationService.getConversationByProduct(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<ConversationDto> createConversation(@RequestBody @Valid CreateConversationDto createConversationDto) {
        return ResponseEntity.ok(this.conversationService.createConversation(createConversationDto));
    }

    @DeleteMapping("/private/{conversationID}")
    @PreAuthorize("@permissionHandler.hasAccessMulti(@conversationDao,#conversationID)")
    public ResponseEntity<Void> deleteConversation(@PathVariable("conversationID") UUID conversationID) {
        this.conversationService.deleteConversation(conversationID);
        return ResponseEntity.noContent().build();
    }
}
