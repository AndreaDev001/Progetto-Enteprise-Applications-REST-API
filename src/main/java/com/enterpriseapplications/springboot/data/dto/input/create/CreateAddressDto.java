package com.enterpriseapplications.springboot.data.dto.input.create;


import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAddressDto
{
    private CountryCode countryCode;
    private String street;
    private String locality;
    private String postalCode;
}
