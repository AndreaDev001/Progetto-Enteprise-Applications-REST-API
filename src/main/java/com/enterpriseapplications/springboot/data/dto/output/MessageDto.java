package com.enterpriseapplications.springboot.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDto extends RepresentationModel<MessageDto>
{
    private UserRef sender;
    private UserRef receiver;
    private String text;
    private LocalDateTime createdDate;
}
