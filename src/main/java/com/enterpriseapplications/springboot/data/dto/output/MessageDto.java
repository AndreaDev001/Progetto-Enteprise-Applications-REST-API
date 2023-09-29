package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "content")
public class MessageDto extends GenericOutput<MessageDto>
{
    private UUID id;
    private UserRef sender;
    private UserRef receiver;
    private String text;
    private LocalDateTime createdDate;
}
