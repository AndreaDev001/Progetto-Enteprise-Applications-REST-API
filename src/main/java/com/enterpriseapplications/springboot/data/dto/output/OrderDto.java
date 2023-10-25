package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
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
public class OrderDto extends GenericOutput<OrderDto>
{
    private UUID id;
    private UserRef buyer;
    private BigDecimal price;
    private AddressDto address;
    private ProductRef product;
    private LocalDate createdDate;
}
