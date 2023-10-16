package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentMethodServiceImp extends GenericServiceImp<PaymentMethod,PaymentMethodDto> implements PaymentMethodService
{
    private final PaymentMethodDao paymentMethodDao;

    public PaymentMethodServiceImp(PaymentMethodDao paymentMethodDao,ModelMapper modelMapper,PagedResourcesAssembler<PaymentMethod> pagedResourcesAssembler) {
        super(modelMapper,PaymentMethod.class,PaymentMethodDto.class,pagedResourcesAssembler);
        this.paymentMethodDao = paymentMethodDao;
    }

    @Override
    public PaymentMethodDto getPaymentMethod(UUID paymentMethodID) {
        PaymentMethod paymentMethod = this.paymentMethodDao.findById(paymentMethodID).orElseThrow();
        return this.modelMapper.map(paymentMethod,PaymentMethodDto.class);
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
