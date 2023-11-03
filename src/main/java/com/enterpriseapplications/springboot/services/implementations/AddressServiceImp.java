package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.AddressDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateAddressDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateAddressDto;
import com.enterpriseapplications.springboot.data.dto.output.AddressDto;
import com.enterpriseapplications.springboot.data.entities.Address;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.AddressSearchResponse;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import com.enterpriseapplications.springboot.services.interfaces.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp extends GenericServiceImp<Address, AddressDto> implements AddressService
{
    private final UserDao userDao;
    private final AddressDao addressDao;
    private final AddressValidationHandler addressValidationHandler;

    public AddressServiceImp(AddressDao addressDao,UserDao userDao,AddressValidationHandler addressValidationHandler,ModelMapper modelMapper,PagedResourcesAssembler<Address> pagedResourcesAssembler) {
        super(modelMapper,Address.class,AddressDto.class,pagedResourcesAssembler);
        this.userDao = userDao;
        this.addressDao = addressDao;
        this.addressValidationHandler = addressValidationHandler;
    }

    @Override
    public PagedModel<AddressDto> getAddresses(Pageable pageable) {
        Page<Address> addresses = this.addressDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(addresses,this.modelAssembler);
    }

    @Override
    public PagedModel<AddressDto> getAddresses(String ownerName, Pageable pageable) {
        Page<Address> addresses = this.addressDao.getAddressesByOwnerName(ownerName,pageable);
        return this.pagedResourcesAssembler.toModel(addresses,this.modelAssembler);
    }

    @Override
    public AddressSearchResponse searchForAddresses(CountryCode countryCode, String query) {
        return this.addressValidationHandler.getAddresses(countryCode,query);
    }

    @Override
    public List<AddressDto> getAddressesByUser(UUID userID) {
        List<Address> addresses = this.addressDao.getAddressesByUser(userID);
        return addresses.stream().map(address -> this.modelMapper.map(address,AddressDto.class)).collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddress(UUID addressID) {
        return this.modelMapper.map(this.addressDao.findById(addressID).orElseThrow(),AddressDto.class);
    }

    @Override
    @Transactional
    public AddressDto createAddress(CreateAddressDto createAddressDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(!this.addressValidationHandler.validateAddress(createAddressDto))
            throw new InvalidFormat("errors.address.invalidAddress");
        Address address = Address.builder().countryCode(createAddressDto.getCountryCode()).postalCode(createAddressDto.getPostalCode()).locality(createAddressDto.getLocality()).street(createAddressDto.getStreet()).ownerName(createAddressDto.getOwnerName()).user(requiredUser).build();
        this.addressDao.save(address);
        return this.modelMapper.map(address,AddressDto.class);
    }

    @Override
    @Transactional
    public AddressDto updateAddress(UpdateAddressDto updateAddressDto) {
        Address address = this.addressDao.findById(updateAddressDto.getAddressID()).orElseThrow();
        if(updateAddressDto.getOwnerName() != null)
            address.setOwnerName(updateAddressDto.getOwnerName());
        this.addressDao.save(address);
        return this.modelMapper.map(address,AddressDto.class);
    }

    @Override
    public CountryCode[] getCountryCodes() {
        return CountryCode.values();
    }

    @Override
    @Transactional
    public void deleteAddress(UUID addressID) {
        this.addressDao.findById(addressID).orElseThrow();
        this.addressDao.deleteById(addressID);
    }
}
