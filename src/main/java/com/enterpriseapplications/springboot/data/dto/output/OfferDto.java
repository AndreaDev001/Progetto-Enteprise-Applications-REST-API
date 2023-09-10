package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto
{
    private UserRef buyer;
    private ProductRef product;
    private String description;
    private BigDecimal price;
    private OfferStatus status;
    private LocalDate createdDate;
    private LocalDate expirationDate;
    private boolean expired;
}
