package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService
{
    PaymentMethodDto getPaymentMethod(UUID paymentMethodID);
    PagedModel<PaymentMethodDto> getPaymentMethods(Pageable pageable);
    List<PaymentMethodDto> getPaymentMethods(UUID ownerID);
    PagedModel<PaymentMethodDto> getPaymentMethodsByBrand(UUID ownerID,String brand,Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByHolderName(UUID ownerID,String name,Pageable pageable);
    PaymentMethodDto createPaymentMethod(CreatePaymentMethodDto createPaymentMethodDto);
    PaymentMethodDto updatePaymentMethod(UpdatePaymentMethodDto updatePaymentMethodDto);
    void deletePaymentMethod(UUID paymentMethodID);
    PaymentMethodBrand[] getBrands();
}
