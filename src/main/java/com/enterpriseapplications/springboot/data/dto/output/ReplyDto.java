package com.enterpriseapplications.springboot.data.dto.output;

import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto
{
    private Long id;
    private String text;
    private UserRef writer;
    private LocalDate createdDate;
}
