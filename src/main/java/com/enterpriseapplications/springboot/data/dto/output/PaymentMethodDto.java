package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto extends GenericOutput<PaymentMethodDto>
{
    private Long id;
    private UserRef owner;
    private String holderName;
    private String number;
    private String country;
    private LocalDate createdDate;
    private LocalDate expirationDate;
}
