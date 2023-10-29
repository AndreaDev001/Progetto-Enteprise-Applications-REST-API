package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderDto
{
    @NotNull
    private UUID productID;

    @NotNull
    private UUID paymentMethodID;

    @NotNull
    private UUID addressID;

    @NotNull
    @Positive
    private BigDecimal price;

}
