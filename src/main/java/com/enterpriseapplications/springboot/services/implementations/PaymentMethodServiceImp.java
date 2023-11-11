package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImp extends GenericServiceImp<PaymentMethod,PaymentMethodDto> implements PaymentMethodService
{
    private final PaymentMethodDao paymentMethodDao;
    private final UserDao userDao;


    public PaymentMethodServiceImp(UserDao userDao,PaymentMethodDao paymentMethodDao,ModelMapper modelMapper,PagedResourcesAssembler<PaymentMethod> pagedResourcesAssembler) {
        super(modelMapper,PaymentMethod.class,PaymentMethodDto.class,pagedResourcesAssembler);
        this.userDao = userDao;
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
    public List<PaymentMethodDto> getPaymentMethods(UUID ownerID) {
        List<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethods(ownerID);
        return paymentMethods.stream().map(paymentMethod -> this.modelMapper.map(paymentMethod,PaymentMethodDto.class)).collect(Collectors.toList());
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethodsByBrand(UUID ownerID, String brand, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByBrand(ownerID,brand,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    public PagedModel<PaymentMethodDto> getPaymentMethodsByHolderName(UUID ownerID, String name, Pageable pageable) {
        Page<PaymentMethod> paymentMethods = this.paymentMethodDao.getPaymentMethodsByHolderName(ownerID,name,pageable);
        return this.pagedResourcesAssembler.toModel(paymentMethods,modelAssembler);
    }

    @Override
    @Transactional
    public PaymentMethodDto createPaymentMethod(CreatePaymentMethodDto createPaymentMethodDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setHolderName(createPaymentMethodDto.getHolderName());
        paymentMethod.setNumber(createPaymentMethodDto.getNumber());
        paymentMethod.setBrand(createPaymentMethodDto.getBrand());
        paymentMethod.setNumber(createPaymentMethodDto.getNumber());
        paymentMethod.setExpirationDate(createPaymentMethodDto.getExpirationDate());
        paymentMethod.setUser(requiredUser);
        paymentMethod = this.paymentMethodDao.save(paymentMethod);
        return this.modelMapper.map(paymentMethod,PaymentMethodDto.class);
    }

    @Override
    @Transactional
    public PaymentMethodDto updatePaymentMethod(UpdatePaymentMethodDto updatePaymentMethodDto) {
        PaymentMethod requiredPaymentMethod = this.paymentMethodDao.findById(updatePaymentMethodDto.getPaymentMethodID()).orElseThrow();
        if(updatePaymentMethodDto.getBrand() != null)
            requiredPaymentMethod.setBrand(updatePaymentMethodDto.getBrand());
        if(updatePaymentMethodDto.getHolderName() != null)
            requiredPaymentMethod.setHolderName(updatePaymentMethodDto.getHolderName());
        return this.modelMapper.map(requiredPaymentMethod,PaymentMethodDto.class);
    }

    @Override
    @Transactional
    public void deletePaymentMethod(UUID paymentMethodID) {
        this.paymentMethodDao.findById(paymentMethodID).orElseThrow();
        this.paymentMethodDao.deleteById(paymentMethodID);
    }

    @Override
    public PaymentMethodBrand[] getBrands() {
        return PaymentMethodBrand.values();
    }
}
