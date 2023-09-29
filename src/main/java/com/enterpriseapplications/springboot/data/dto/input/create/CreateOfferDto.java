package com.enterpriseapplications.springboot.data.dto.input.create;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferDto
{
    private BigDecimal price;
    private String description;
    private UUID productID;
}
