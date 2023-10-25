package com.enterpriseapplications.springboot.data.dto.input.update.offers;

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
public class UpdateOfferBuyerDto
{
    @NotNull
    private UUID offerID;
    private String description;
    private BigDecimal price;
}
