package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateAddressDto;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchRequest;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchResponse;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class AddressValidationHandler
{
    private final String authenticationToken = "W33SRF3lONzo8lY6aSdQ";
    private final String authenticationID = "e20f6d46-b863-6240-6b5e-c9c38949c5fc";
    private final String baseURI =  "https://international-autocomplete.api.smarty.com";

    public AddressSearchResponse getAddresses(CountryCode countryCode,String query)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            AddressSearchRequest addressSearchRequest = generateAddressSearchRequest(countryCode,query);
            String resourceURI = this.baseURI + "/lookup?" + addressSearchRequest.toString();
            return restTemplate.getForObject(resourceURI,AddressSearchResponse.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean validateAddress(CreateAddressDto createAddressDto) {
        return false;
    }

    private AddressSearchRequest generateAddressSearchRequest(CountryCode countryCode,String query) {
        AddressSearchRequest addressSearchRequest = new AddressSearchRequest();
        addressSearchRequest.setAuthenticationID(authenticationID);
        addressSearchRequest.setAuthenticationToken(authenticationToken);
        addressSearchRequest.setCountryCode(countryCode);
        addressSearchRequest.setQuery(query);
        return addressSearchRequest;
    }
}
