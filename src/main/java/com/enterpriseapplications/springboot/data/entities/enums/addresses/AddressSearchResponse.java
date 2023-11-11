package com.enterpriseapplications.springboot.data.entities.enums.addresses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSearchResponse
{
    public List<AddressItem> candidates;
}
