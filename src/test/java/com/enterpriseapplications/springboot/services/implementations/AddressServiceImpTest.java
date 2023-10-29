package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.AddressDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateAddressDto;
import com.enterpriseapplications.springboot.data.dto.output.AddressDto;
import com.enterpriseapplications.springboot.data.entities.Address;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AddressServiceImpTest extends GenericTestImp<Address,AddressDto>
{
    private AddressServiceImp addressServiceImp;
    @Mock
    private AddressDao addressDao;
    @Mock
    private AddressValidationHandler addressValidationHandler;
    @Mock
    private UserDao userDao;



    @Override
    protected boolean valid(Address address, AddressDto addressDto) {
        Assert.assertNotNull(addressDto);
        Assert.assertEquals(address.getCountryCode(),addressDto.getCountryCode());
        Assert.assertEquals(address.getStreet(),addressDto.getStreet());
        Assert.assertEquals(address.getLocality(),addressDto.getLocality());
        Assert.assertEquals(address.getOwnerName(),addressDto.getOwnerName());
        Assert.assertEquals(address.getPostalCode(),addressDto.getPostalCode());
        return true;
    }

    @Override
    protected void init() {
        super.init();
        addressServiceImp = new AddressServiceImp(addressDao,userDao,addressValidationHandler,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        firstElement = Address.builder().countryCode(CountryCode.EN).locality("Locality").postalCode("88900").street("Street").ownerName("Owner").user(firstUser).build();
        secondElement = Address.builder().countryCode(CountryCode.EN).locality("Locality").postalCode("82000").street("Street").ownerName("Owner").user(secondUser).build();
        elements = List.of(firstElement,secondElement);
    }


    @Before
    public void before() {
        this.init();
    }

    @Test
    public void getAddresses() {
        given(this.addressDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.addressDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        AddressDto firstAddress = this.addressServiceImp.getAddress(firstElement.getId());
        AddressDto secondAddress  = this.addressServiceImp.getAddress(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstAddress));
        Assert.assertTrue(valid(secondElement,secondAddress));
    }

    @Test
    public void getAddressesByUser() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.addressDao.getAddressesByUser(user.getId())).willReturn(elements);
        List<AddressDto> addresses = this.addressServiceImp.getAddressesByUser(user.getId());
        Assert.assertTrue(compare(elements,addresses));
    }

    @Test
    public void getAddress() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.addressDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<AddressDto> pagedModel = this.addressServiceImp.getAddresses(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void createAddress() {
        CreateAddressDto createAddressDto = new CreateAddressDto();
        given(this.addressValidationHandler.validateAddress(createAddressDto)).willReturn(true);
    }
}
