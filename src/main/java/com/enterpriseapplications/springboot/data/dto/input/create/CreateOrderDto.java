package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto
{
    @NotNull
    @PositiveOrZero
    private Long productID;

    @NotNull
    @Positive
    private BigDecimal price;
}
