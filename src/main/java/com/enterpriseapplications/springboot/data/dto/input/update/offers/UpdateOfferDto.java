package com.enterpriseapplications.springboot.data.dto.input.update.offers;


import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOfferDto
{
    @NotNull
    private UUID offerID;
    private String description;
    private BigDecimal price;
    private OfferStatus offerStatus;
    private LocalDate expirationDate;
}
