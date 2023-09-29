package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.services.interfaces.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController
{
    private final ReplyService replyService;

    @GetMapping("/review/{reviewID}")
    public ResponseEntity<ReplyDto> getReply(@PathVariable("reviewID") UUID id) {
        return ResponseEntity.ok(this.replyService.getReply(id));
    }

    @GetMapping("/writer/{writerID}")
    public ResponseEntity<PagedModel<ReplyDto>> getReplies(@PathVariable("writerID") UUID id, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReplyDto> replies = this.replyService.getWrittenReplies(id, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(replies);
    }

    @PostMapping
    public ResponseEntity<ReplyDto> createReply(@RequestBody @Valid CreateReplyDto createReplyDto) {
        return ResponseEntity.ok(this.replyService.createReply(createReplyDto));
    }

    @PutMapping
    public ResponseEntity<ReplyDto> updateReply(@RequestBody @Valid UpdateReplyDto updateReplyDto) {
        return ResponseEntity.ok(this.replyService.updateReply(updateReplyDto));
    }

    @DeleteMapping("{replyID}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyID") UUID replyID) {
        this.replyService.deleteReply(replyID);
        return ResponseEntity.noContent().build();
    }
}
