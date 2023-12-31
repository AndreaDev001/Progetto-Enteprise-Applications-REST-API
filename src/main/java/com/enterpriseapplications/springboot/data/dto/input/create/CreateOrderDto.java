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
    public UUID productID;

    @NotNull
    public UUID paymentMethodID;

    @NotNull
    public UUID addressID;

    @NotNull
    @Positive
    public BigDecimal price;
}
