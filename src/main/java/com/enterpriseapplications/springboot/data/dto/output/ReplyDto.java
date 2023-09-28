package com.enterpriseapplications.springboot.data.dto.output;

import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto extends GenericOutput<ReplyDto>
{
    private Long id;
    private String text;
    private UserRef writer;
    private LocalDate createdDate;
}
