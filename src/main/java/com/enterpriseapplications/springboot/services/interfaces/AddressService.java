package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateAddressDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateAddressDto;
import com.enterpriseapplications.springboot.data.dto.output.AddressDto;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchRequest;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchResponse;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface AddressService
{
    PagedModel<AddressDto> getAddresses(Pageable pageable);
    PagedModel<AddressDto> getAddresses(String ownerName,Pageable pageable);
    AddressSearchResponse searchForAddresses(CountryCode countryCode,String query);
    List<AddressDto> getAddressesByUser(UUID userID);
    AddressDto getAddress(UUID addressID);
    AddressDto createAddress(CreateAddressDto createAddressDto);
    AddressDto updateAddress(UpdateAddressDto updateAddressDto);
    CountryCode[] getCountryCodes();
    void deleteAddress(UUID addressID);
}
