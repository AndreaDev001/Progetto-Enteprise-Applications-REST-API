package com.enterpriseapplications.springboot.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageDto
{
    private UserRef sender;
    private UserRef receiver;
    private String text;
    private LocalDateTime createdDate;
}
