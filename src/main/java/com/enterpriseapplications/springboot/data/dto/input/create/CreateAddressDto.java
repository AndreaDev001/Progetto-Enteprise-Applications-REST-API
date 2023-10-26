package com.enterpriseapplications.springboot.data.dto.input.create;


import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAddressDto
{
    @NotNull
    private CountryCode countryCode;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String locality;

    @NotNull
    @NotBlank
    private String postalCode;

    @NotNull
    @NotBlank
    private String ownerName;
}
