package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImp implements PaymentMethodService
{
    private final PaymentMethodDao paymentMethodDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<PaymentMethod,PaymentMethodDto> modelAssembler;
    private final PagedResourcesAssembler<PaymentMethod> pagedResourcesAssembler;


    public PaymentMethodServiceImp(PaymentMethodDao paymentMethodDao,ModelMapper modelMapper,PagedResourcesAssembler<PaymentMethod> pagedResourcesAssembler) {
        this.paymentMethodDao = paymentMethodDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(PaymentMethod.class,PaymentMethodDto.class,modelMapper);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethods(Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethods(UUID ownerID, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethods(ownerID,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethodsByBrand(UUID ownerID, String brand, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByBrand(ownerID,brand,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethodsByCountry(UUID ownerID, String country, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByCountry(ownerID,country,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethodsByHolderName(UUID ownerID, String name, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByHolderName(ownerID,name,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public void deletePaymentMethod(UUID paymentMethodID) {
        this.paymentMethodDao.findById(paymentMethodID).orElseThrow();
        this.paymentMethodDao.deleteById(paymentMethodID);
    }
}
