package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReviewDto;
import com.enterpriseapplications.springboot.services.interfaces.ReplyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ReplyController
{
    private final ReplyService replyService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReplyDto>> getReplies(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReplyDto> replies = this.replyService.getReplies(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/private/{replyID}")
    @PreAuthorize("@permissionHandler.hasAccess(@replyDao,#replyID)")
    public ResponseEntity<ReplyDto> getReply(@PathVariable("replyID") UUID replyID) {
        return ResponseEntity.ok(this.replyService.getReply(replyID));
    }

    @GetMapping("/public/review/{reviewID}")
    public ResponseEntity<ReplyDto> getReplyByReview(@PathVariable("reviewID") UUID reviewID) {
        return ResponseEntity.ok(this.replyService.getReplyByReview(reviewID));
    }

    @GetMapping("/private/writer/{writerID}")
    @PreAuthorize("@permissionHandler.hasAccess(#writerID)")
    public ResponseEntity<PagedModel<ReplyDto>> getReplies(@PathVariable("writerID") UUID writerID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReplyDto> replies = this.replyService.getWrittenReplies(writerID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(replies);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<ReplyDto> createReply(@RequestBody @Valid CreateReplyDto createReplyDto) {
        return ResponseEntity.ok(this.replyService.createReply(createReplyDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@replyDao,#updateReplyDto.replyID)")
    public ResponseEntity<ReplyDto> updateReply(@RequestBody @Valid UpdateReplyDto updateReplyDto) {
        return ResponseEntity.ok(this.replyService.updateReply(updateReplyDto));
    }

    @DeleteMapping("/private/{replyID}")
    @PreAuthorize("@permissionHandler.hasAccess(@replyDao,#replyID)")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyID") UUID replyID) {
        this.replyService.deleteReply(replyID);
        return ResponseEntity.noContent().build();
    }
}
