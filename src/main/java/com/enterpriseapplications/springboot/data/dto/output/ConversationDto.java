package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "content")
public class ConversationDto extends GenericOutput<ConversationDto>
{
    private UUID id;
    private UserRef first;
    private UserRef second;
    private ProductRef product;
    private LocalDate createdDate;
}
