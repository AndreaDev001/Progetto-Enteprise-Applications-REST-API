package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
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
public class ReviewDto extends GenericOutput<ReviewDto>
{
    private UUID id;
    private UserRef writer;
    private UserRef receiver;
    private Integer rating;
    private String text;
    private ReplyDto reply;
    private LocalDate createdDate;
}
