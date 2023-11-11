package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
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
public class BanDto extends GenericOutput<BanDto>
{
    private UUID id;
    private UserRef banner;
    private UserRef banned;
    private String description;
    private ReportReason reason;
    private boolean expired;
    private LocalDate createdDate;
    private LocalDate expirationDate;
}
