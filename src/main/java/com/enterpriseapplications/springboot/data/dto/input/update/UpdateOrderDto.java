package com.enterpriseapplications.springboot.data.dto.input.update;


import jakarta.validation.constraints.NotNull;
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
public class UpdateOrderDto
{
    @NotNull
    private UUID orderID;
    private BigDecimal price;
}
