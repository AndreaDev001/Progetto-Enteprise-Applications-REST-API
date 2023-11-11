package com.enterpriseapplications.springboot.data.dto.output;

import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.Reply;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "content")
public class ReplyDto extends GenericOutput<ReplyDto>
{
    private UUID id;
    private String text;
    private UserRef writer;
    private UUID reviewID;
    private LocalDate createdDate;
}
