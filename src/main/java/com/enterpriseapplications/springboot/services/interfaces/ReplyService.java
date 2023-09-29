package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ReplyService
{
    PagedModel<ReplyDto>  getReplies(Pageable pageable);
    ReplyDto getReply(UUID reviewID);
    ReplyDto createReply(CreateReplyDto createReplyDto);
    ReplyDto updateReply(UpdateReplyDto updateReplyDto);
    PagedModel<ReplyDto> getWrittenReplies(UUID userID, Pageable pageable);
    void deleteReply(UUID replyID);
}
