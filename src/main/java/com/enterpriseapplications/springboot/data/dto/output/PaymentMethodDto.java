package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
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
public class PaymentMethodDto extends GenericOutput<PaymentMethodDto>
{
    private UUID id;
    private UserRef user;
    private String holderName;
    private String number;
    private PaymentMethodBrand brand;
    private LocalDate createdDate;
    private LocalDate expirationDate;
}
