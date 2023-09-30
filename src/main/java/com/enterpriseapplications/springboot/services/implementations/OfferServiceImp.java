package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.OfferDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImp implements OfferService
{
    private final OfferDao offerDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Offer,OfferDto> modelAssembler;
    private final PagedResourcesAssembler<Offer> pagedResourcesAssembler;

    public OfferServiceImp(OfferDao offerDao,UserDao userDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<Offer> pagedResourcesAssembler) {
        this.offerDao = offerDao;
        this.userDao = userDao;
        this.productDao = productDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Offer.class,OfferDto.class,modelMapper);
    }

    @Override
    public OfferDto getOffer(UUID offerID) {
        Offer offer = this.offerDao.findById(offerID).orElseThrow();
        return this.modelMapper.map(offer,OfferDto.class);
    }


    @Override
    public OfferSpecifications.OrderType[] getOrderTypes() {
        return OfferSpecifications.OrderType.values();
    }

    @Override
    public PagedModel<OfferDto> getOffers(Pageable pageable) {
        Page<Offer> offers = this.offerDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersByStatus(OfferStatus offerStatus, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByStatus(offerStatus,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersByExpired(boolean expired, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByExpired(expired,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersByBuyerID(UUID buyerID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByBuyerID(buyerID,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersByProductID(UUID productID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByProductID(productID,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersByProductIDAndStatus(UUID productID, OfferStatus status, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByProductAndStatus(productID,status,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getOffersBySpec(Specification<Offer> specification, Pageable pageable) {
        Page<Offer> offers = this.offerDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    @Transactional
    public OfferDto createOffer(CreateOfferDto createOfferDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(createOfferDto.getProductID()).orElseThrow();
        if(requiredProduct.getSeller().getId().equals(requiredUser.getId()))
            throw new InvalidFormat("errors.offer.invalidBidder");
        Offer offer = new Offer();
        offer.setBuyer(requiredUser);
        offer.setProduct(requiredProduct);
        offer.setStatus(OfferStatus.OPEN);
        offer.setDescription(createOfferDto.getDescription());
        offer.setPrice(createOfferDto.getPrice());
        this.offerDao.save(offer);
        return this.modelMapper.map(offer,OfferDto.class);
    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerID) {
        this.offerDao.findById(offerID).orElseThrow();
        this.offerDao.deleteById(offerID);
    }
}
