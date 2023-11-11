package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(collectionRelation = "content")
public class OfferDto extends GenericOutput<OfferDto>
{
    private UUID id;
    private UserRef buyer;
    private ProductRef product;
    private String description;
    private BigDecimal price;
    private OfferStatus status;
    private LocalDate createdDate;
    private LocalDate expirationDate;
    private boolean expired;
}
