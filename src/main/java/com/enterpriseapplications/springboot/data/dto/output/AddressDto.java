package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto extends GenericOutput<AddressDto>
{
    private UUID id;
    private UserRef user;
    private CountryCode countryCode;
    private String street;
    private String locality;
    private String postalCode;
    private LocalDate createdDate;
}
