package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
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
public class LikeDto extends GenericOutput<LikeDto>
{
    private UUID id;
    private UserRef user;
    private ProductRef product;
    private LocalDate createdDate;
}
