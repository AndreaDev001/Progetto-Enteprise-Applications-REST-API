package com.enterpriseapplications.springboot.data.entities.enums.addresses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressItem
{
    public String street;
    public String locality;
    public String administrative_area;
    public String super_administrative_area;
    public String postal_code;
    public String country_iso3;
}
