package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.data.entities.Reply;
import com.enterpriseapplications.springboot.services.interfaces.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController
{
    private final ReplyService replyService;

    @GetMapping("/review/{reviewID}")
    public ResponseEntity<ReplyDto> getReply(@PathVariable("reviewID") Long id) {
        return ResponseEntity.ok(this.replyService.getReply(id));
    }

    @GetMapping("/writer/{writerID}")
    public ResponseEntity<PaginationResponse<ReplyDto>> getReplies(@PathVariable("writerID") Long id, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReplyDto> replies = this.replyService.getWrittenReplies(id, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(replies.toList(),paginationRequest.getPage(),paginationRequest.getPage(),replies.getTotalPages(),replies.getTotalElements()));
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
    public ResponseEntity<Void> deleteReply(@PathVariable("replyID") Long replyID) {
        this.replyService.deleteReply(replyID);
        return ResponseEntity.noContent().build();
    }
}
