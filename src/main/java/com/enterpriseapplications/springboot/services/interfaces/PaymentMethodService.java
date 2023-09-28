package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface PaymentMethodService
{
    PagedModel<PaymentMethodDto> getPaymentMethods(Long ownerID, Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByBrand(Long ownerID,String brand,Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByCountry(Long ownerID,String country,Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByHolderName(Long ownerID,String name,Pageable pageable);
    void deletePaymentMethod(Long paymentMethodID);
}
