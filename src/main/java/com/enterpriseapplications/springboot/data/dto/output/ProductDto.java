package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto
{
    private String name;
    private String description;
    private String brand;
    private ProductCondition condition;
    private BigDecimal price;
    private UserRef seller;
    private LocalDate createdDate;
}
