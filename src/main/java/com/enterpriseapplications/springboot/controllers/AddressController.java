package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateAddressDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateAddressDto;
import com.enterpriseapplications.springboot.data.dto.output.AddressDto;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchResponse;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import com.enterpriseapplications.springboot.services.implementations.AddressValidationHandler;
import com.enterpriseapplications.springboot.services.interfaces.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class AddressController
{
    private final AddressService addressService;

    @GetMapping("/private")
    public ResponseEntity<PagedModel<AddressDto>> getAddresses(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<AddressDto> addresses = this.addressService.getAddresses(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(addresses);
    }
    @GetMapping("/private/{addressID}")
    @PreAuthorize("@permissionHandler.hasAccess(@addressDao,#addressID")
    public ResponseEntity<AddressDto> getAddress(@PathVariable("addressID") UUID addressID) {
        return ResponseEntity.ok(this.addressService.getAddress(addressID));
    }
    @GetMapping("/public/query")
    public ResponseEntity<AddressSearchResponse> searchForAddresses(@RequestParam("countryCode") CountryCode code,@RequestParam("query") String query)
    {
        return ResponseEntity.ok(this.addressService.searchForAddresses(code, query));
    }

    @GetMapping("/public/countries")
    public ResponseEntity<CountryCode[]> getCountries() {
        return ResponseEntity.ok(this.addressService.getCountryCodes());
    }

    @GetMapping("/private/owner/{ownerName}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<AddressDto>> getAddressesByOwnerName(@PathVariable("ownerName") String ownerName,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<AddressDto> addresses = this.addressService.getAddresses(ownerName,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<List<AddressDto>> getAddresses(@PathVariable("userID") UUID userID) {
        List<AddressDto> addresses = this.addressService.getAddressesByUser(userID);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid CreateAddressDto createAddressDto) {
        return ResponseEntity.ok(this.addressService.createAddress(createAddressDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@addressDao,#updateAddressDto.addressID)")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody @Valid UpdateAddressDto updateAddressDto) {
        return ResponseEntity.ok(this.addressService.updateAddress(updateAddressDto));
    }

    @DeleteMapping("/private/{addressID}")
    @PreAuthorize("@permissionHandler.hasAccess(@addressDao,#addressID")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable("addressID") UUID addressID) {
        this.addressService.deleteAddress(addressID);
        return ResponseEntity.noContent().build();
    }
}
