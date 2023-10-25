package com.enterpriseapplications.springboot.data.dto.input.update.offers;


import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOfferSellerDto
{
    @NotNull
    private UUID offerID;
    @NotNull
    private UUID productID;
    @NotNull
    private OfferStatus offerStatus;
}
