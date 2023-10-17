package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ConversationRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "content")
public class MessageDto extends GenericOutput<MessageDto>
{
    private UUID id;
    private UserRef sender;
    private UserRef receiver;
    private String text;
    private ConversationRef conversatio;
    private LocalDateTime createdDate;
}
