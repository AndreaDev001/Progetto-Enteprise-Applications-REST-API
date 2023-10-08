package com.enterpriseapplications.springboot.data.dto.output.reports;


import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
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
public class ReportDto extends GenericOutput<ReportDto>
{
    protected UUID id;
    protected UserRef reporter;
    protected UserRef reported;
    protected String description;
    protected ReportReason reason;
    protected ReportType type;
    protected LocalDate createdDate;
}
