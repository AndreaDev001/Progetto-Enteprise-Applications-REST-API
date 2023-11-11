package com.enterpriseapplications.springboot.data.entities.enums.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressSearchRequest
{
    private String authenticationID;
    private String authenticationToken;
    private CountryCode countryCode;
    private String query;

    @Override
    public String toString() {
        return "auth-id=" + authenticationID + "&" + "auth-token=" + authenticationToken + "&" + "country=" + countryCode.toString() + "&" + "search=" + query;
    }
}
