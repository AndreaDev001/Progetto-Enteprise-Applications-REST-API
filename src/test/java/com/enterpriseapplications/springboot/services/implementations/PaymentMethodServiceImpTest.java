package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImpTest extends GenericTestImp<PaymentMethod,PaymentMethodDto> {

    private PaymentMethodServiceImp paymentMethodServiceImp;
    @Mock
    private PaymentMethodDao paymentMethodDao;
    @Mock
    private UserDao userDao;


    @Override
    protected void init() {
        super.init();
        paymentMethodServiceImp = new PaymentMethodServiceImp(userDao,paymentMethodDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        firstElement = PaymentMethod.builder().id(UUID.randomUUID()).brand(PaymentMethodBrand.VISA).user(firstUser).number("234568279102").holderName("Holder Name").build();
        secondElement = PaymentMethod.builder().id(UUID.randomUUID()).brand(PaymentMethodBrand.DISCOVER).user(secondUser).number("37238393910").holderName("Holder Name").build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }

    public boolean valid(PaymentMethod paymentMethod, PaymentMethodDto paymentMethodDto) {
        Assert.assertNotNull(paymentMethodDto);
        Assert.assertEquals(paymentMethod.getId(),paymentMethodDto.getId());
        Assert.assertEquals(paymentMethod.getNumber(),paymentMethodDto.getNumber());
        Assert.assertEquals(paymentMethod.getBrand(),paymentMethodDto.getBrand());
        Assert.assertEquals(paymentMethod.getHolderName(),paymentMethodDto.getHolderName());
        Assert.assertEquals(paymentMethod.getUser().getId(),paymentMethodDto.getUser().getId());
        Assert.assertEquals(paymentMethod.getCreatedDate(),paymentMethodDto.getCreatedDate());
        return true;
    }

    @Test
    void getPaymentMethod() {
        given(this.paymentMethodDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.paymentMethodDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        PaymentMethodDto firstPaymentMethodDto = this.paymentMethodServiceImp.getPaymentMethod(firstElement.getId());
        PaymentMethodDto secondPaymentMethodDto = this.paymentMethodServiceImp.getPaymentMethod(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstPaymentMethodDto));
        Assert.assertTrue(valid(secondElement,secondPaymentMethodDto));
    }

    @Test
    void getPaymentMethods() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.paymentMethodDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<PaymentMethodDto> pagedModel = this.paymentMethodServiceImp.getPaymentMethods(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void getPaymentMethodByOwner() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.paymentMethodDao.getPaymentMethods(user.getId())).willReturn(elements);
        List<PaymentMethodDto> paymentMethods = this.paymentMethodServiceImp.getPaymentMethods(user.getId());
        Assert.assertTrue(compare(elements,paymentMethods));
    }

    @Test
    void getPaymentMethodsByBrand() {
        User user = User.builder().id(UUID.randomUUID()).build();
        String brand = "brand";
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.paymentMethodDao.getPaymentMethodsByBrand(user.getId(),brand,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<PaymentMethodDto> pagedModel = this.paymentMethodServiceImp.getPaymentMethodsByBrand(user.getId(),brand,pageRequest);
        Assert.assertTrue(compare(elements, pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void getPaymentMethodsByHolderName() {
        User user = User.builder().id(UUID.randomUUID()).build();
        String holderName = "Holder Name";
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.paymentMethodDao.getPaymentMethodsByHolderName(user.getId(),holderName,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<PaymentMethodDto> pagedModel = this.paymentMethodServiceImp.getPaymentMethodsByHolderName(user.getId(),holderName,pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void updatePaymentMethod() {
        PaymentMethod paymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).build();
        UpdatePaymentMethodDto updatePaymentMethod = UpdatePaymentMethodDto.builder().paymentMethodID(paymentMethod.getId()).brand(PaymentMethodBrand.VISA).build();
        given(this.paymentMethodDao.findById(paymentMethod.getId())).willReturn(Optional.of(firstElement));
        given(this.paymentMethodDao.save(any(PaymentMethod.class))).willReturn(firstElement);
        PaymentMethodDto result = this.paymentMethodServiceImp.updatePaymentMethod(updatePaymentMethod);
        Assert.assertTrue(valid(firstElement,result));
    }
}