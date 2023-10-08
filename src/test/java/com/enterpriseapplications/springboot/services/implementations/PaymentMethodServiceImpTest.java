package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.data.entities.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImpTest {

    private PaymentMethod firstPaymentMethod;
    private PaymentMethod secondPaymentMethod;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<PaymentMethod> pagedResourcesAssembler;
    private PaymentMethodServiceImp paymentMethodServiceImp;

    @Mock
    private PaymentMethodDao paymentMethodDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        paymentMethodServiceImp = new PaymentMethodServiceImp(paymentMethodDao,modelMapper,pagedResourcesAssembler);
        firstPaymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).brand("brand").country("Italy").owner(firstUser).number("234568279102").holderName("Holder Name").build();
        secondPaymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).brand("brand").country("UK").owner(secondUser).number("37238393910").holderName("Holder Name").build();

    }

    boolean valid(PaymentMethod paymentMethod, PaymentMethodDto paymentMethodDto) {
        Assert.assertNotNull(paymentMethodDto);
        Assert.assertEquals(paymentMethod.getId(),paymentMethodDto.getId());
        Assert.assertEquals(paymentMethod.getNumber(),paymentMethod.getNumber());
        Assert.assertEquals(paymentMethod.getBrand(),paymentMethod.getBrand());
        Assert.assertEquals(paymentMethod.getCountry(),paymentMethod.getCountry());
        Assert.assertEquals(paymentMethod.getHolderName(),paymentMethod.getHolderName());
        Assert.assertEquals(paymentMethod.getOwner().getId(),paymentMethod.getOwnerID());
        Assert.assertEquals(paymentMethod.getCreatedDate(),paymentMethod.getCreatedDate());
        return true;
    }
    @Test
    void getPaymentMethod() {
        given(this.paymentMethodDao.findById(firstPaymentMethod.getId())).willReturn(Optional.of(firstPaymentMethod));
        given(this.paymentMethodDao.findById(secondPaymentMethod.getId())).willReturn(Optional.of(secondPaymentMethod));
        PaymentMethodDto firstPaymentMethodDto = this.paymentMethodServiceImp.getPaymentMethod(firstPaymentMethod.getId());
        PaymentMethodDto secondPaymentMethodDto = this.paymentMethodServiceImp.getPaymentMethod(secondPaymentMethod.getId());
        Assert.assertTrue(valid(firstPaymentMethod,firstPaymentMethodDto));
        Assert.assertTrue(valid(secondPaymentMethod,secondPaymentMethodDto));
    }

    @Test
    void getPaymentMethods() {
    }

    @Test
    void testGetPaymentMethods() {
    }

    @Test
    void getPaymentMethodsByBrand() {
    }

    @Test
    void getPaymentMethodsByCountry() {
    }

    @Test
    void getPaymentMethodsByHolderName() {
    }
}