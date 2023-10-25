package com.enterpriseapplications.springboot.data.dto.input.update;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePaymentMethodDto {

    @NotNull
    private UUID paymentMethodID;
    private String holderName;
    private String brand;
}
