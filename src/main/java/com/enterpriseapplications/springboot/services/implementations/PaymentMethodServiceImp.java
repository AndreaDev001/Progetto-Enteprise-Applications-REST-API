package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImp implements PaymentMethodService
{
    private final PaymentMethodDao paymentMethodDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<PaymentMethodDto> getPaymentMethods(Long ownerID, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethods(ownerID,pageable);
        return new PageImpl<>(paymentMethods.stream().map(paymentMethod -> this.modelMapper.map(paymentMethod,PaymentMethodDto.class)).collect(Collectors.toList()),pageable,paymentMethods.getTotalElements());
    }

    @Override
    public Page<PaymentMethodDto> getPaymentMethodsByBrand(Long ownerID, String brand, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByBrand(ownerID,brand,pageable);
        return new PageImpl<>(paymentMethods.stream().map(paymentMethod -> this.modelMapper.map(paymentMethod,PaymentMethodDto.class)).collect(Collectors.toList()),pageable,paymentMethods.getTotalElements());
    }

    @Override
    public Page<PaymentMethodDto> getPaymentMethodsByCountry(Long ownerID, String country, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByCountry(ownerID,country,pageable);
        return new PageImpl<>(paymentMethods.stream().map(paymentMethod -> this.modelMapper.map(paymentMethod,PaymentMethodDto.class)).collect(Collectors.toList()),pageable,paymentMethods.getTotalElements());
    }

    @Override
    public Page<PaymentMethodDto> getPaymentMethodsByHolderName(Long ownerID, String name, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByHolderName(ownerID,name,pageable);
        return new PageImpl<>(paymentMethods.stream().map(paymentMethod -> this.modelMapper.map(paymentMethod,PaymentMethodDto.class)).collect(Collectors.toList()),pageable,paymentMethods.getTotalElements());
    }

    @Override
    public void deletePaymentMethod(Long paymentMethodID) {
        this.paymentMethodDao.findById(paymentMethodID).orElseThrow();
        this.paymentMethodDao.deleteById(paymentMethodID);
    }
}
