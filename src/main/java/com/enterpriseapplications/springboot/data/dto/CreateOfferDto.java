package com.enterpriseapplications.springboot.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferDto
{
    private BigDecimal price;
    private String description;
    private Long productID;
}
