package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentMethodService
{
    Page<PaymentMethodDto> getPaymentMethods(Long ownerID, Pageable pageable);
    Page<PaymentMethodDto> getPaymentMethodsByBrand(Long ownerID,String brand,Pageable pageable);
    Page<PaymentMethodDto> getPaymentMethodsByCountry(Long ownerID,String country,Pageable pageable);
    Page<PaymentMethodDto> getPaymentMethodsByHolderName(Long ownerID,String name,Pageable pageable);
    void deletePaymentMethod(Long paymentMethodID);
}
