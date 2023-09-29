package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class PaymentMethodDto extends GenericOutput<PaymentMethodDto>
{
    private UUID id;
    private UserRef owner;
    private String holderName;
    private String number;
    private String country;
    private LocalDate createdDate;
    private LocalDate expirationDate;
}
