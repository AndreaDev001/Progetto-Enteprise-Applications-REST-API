package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDto extends GenericOutput<MessageDto>
{
    private UserRef sender;
    private UserRef receiver;
    private String text;
    private LocalDateTime createdDate;
}
