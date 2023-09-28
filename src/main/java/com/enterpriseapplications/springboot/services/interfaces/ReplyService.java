package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService
{
    ReplyDto getReply(Long reviewID);
    ReplyDto createReply(CreateReplyDto createReplyDto);
    ReplyDto updateReply(UpdateReplyDto updateReplyDto);
    Page<ReplyDto> getWrittenReplies(Long userID, Pageable pageable);
    void deleteReply(Long replyID);
}
