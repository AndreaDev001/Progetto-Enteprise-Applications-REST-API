package com.enterpriseapplications.springboot.data.dto.input.update;


import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto
{
    @NotNull
    @PositiveOrZero
    private Long productID;

    private String description;
    private String brand;
    private BigDecimal price;
    private ProductCondition condition;
    private ProductVisibility visibility;
}
