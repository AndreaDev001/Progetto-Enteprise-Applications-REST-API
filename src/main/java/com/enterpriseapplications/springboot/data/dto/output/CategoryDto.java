package com.enterpriseapplications.springboot.data.dto.output;


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
public class CategoryDto extends GenericOutput<CategoryDto>
{
    private UUID id;
    private String primary;
    private String secondary;
    private String tertiary;
    private LocalDate createdDate;
}
