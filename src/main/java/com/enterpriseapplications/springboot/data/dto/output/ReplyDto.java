package com.enterpriseapplications.springboot.data.dto.output;

import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class ReplyDto extends GenericOutput<ReplyDto>
{
    private UUID id;
    private String text;
    private UserRef writer;
    private UUID reviewID;
    private LocalDate createdDate;
}
