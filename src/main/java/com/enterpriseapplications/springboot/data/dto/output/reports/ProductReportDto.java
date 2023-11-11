package com.enterpriseapplications.springboot.data.dto.output.reports;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class ProductReportDto extends ReportDto {
    private ProductRef product;
}
