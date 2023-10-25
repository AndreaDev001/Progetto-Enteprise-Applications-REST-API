package com.enterpriseapplications.springboot.data.dto.input.create;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentMethodDto {

    @NotNull
    @NotBlank
    private String holderName;
    @Pattern(regexp = "[0-9]{16,19}")
    private String number;
    @NotNull
    @NotBlank
    private String brand;
    @NotNull
    @Future
    private LocalDate expirationDate;
}
