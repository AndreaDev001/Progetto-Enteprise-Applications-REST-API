package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.OfferDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferBuyerDto;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferSellerDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OfferServiceImp extends GenericServiceImp<Offer,OfferDto> implements OfferService
{
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss");
    private final OfferDao offerDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public OfferServiceImp(OfferDao offerDao,UserDao userDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<Offer> pagedResourcesAssembler) {
        super(modelMapper,Offer.class,OfferDto.class,pagedResourcesAssembler);
        this.offerDao = offerDao;
        this.userDao = userDao;
        this.productDao = productDao;
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
    public PagedModel<OfferDto> getCreatedOffers(UUID userID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getCreatedOffers(userID, pageable);
        return this.pagedResourcesAssembler.toModel(offers,modelAssembler);
    }

    @Override
    public PagedModel<OfferDto> getReceivedOffers(UUID userID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getReceivedOffers(userID,pageable);
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
    public PagedModel<OfferDto> getSimilarOffers(UUID offerID, Pageable pageable) {
        Offer requiredOffer = this.offerDao.findById(offerID).orElseThrow();
        OfferSpecifications.Filter filter = new OfferSpecifications.Filter(SpecificationsUtils.OrderMode.DESCENDED,requiredOffer);
        Page<Offer> offers = this.offerDao.findAll(OfferSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(offers,this.modelAssembler);
    }

    @Override
    public OfferDto getOffer(UUID userID, UUID productID) {
        Offer offer = this.offerDao.getOffer(userID,productID).orElseThrow();
        return this.modelMapper.map(offer,OfferDto.class);
    }

    @Override
    @Transactional
    public OfferDto createOffer(CreateOfferDto createOfferDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(createOfferDto.getProductID()).orElseThrow();
        if(requiredProduct.getSeller().getId().equals(requiredUser.getId()))
            throw new InvalidFormat("errors.offer.create.invalidBidder");
        if(requiredProduct.getMinPrice().compareTo(createOfferDto.getPrice()) > 0)
            throw new InvalidFormat("errors.offer.create.invalidPrice");
        Offer offer = new Offer();
        offer.setBuyer(requiredUser);
        offer.setProduct(requiredProduct);
        offer.setStatus(OfferStatus.OPEN);
        offer.setDescription(createOfferDto.getDescription());
        offer.setPrice(createOfferDto.getPrice());
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(3);
        offer.setExpirationDate(localDate);
        offer = this.offerDao.save(offer);
        return this.modelMapper.map(offer,OfferDto.class);
    }

    @Override
    @Transactional
    public OfferDto updateOffer(UpdateOfferDto updateOfferDto) {
        Offer requiredOffer = this.offerDao.findById(updateOfferDto.getOfferID()).orElseThrow();
        if(updateOfferDto.getOfferStatus() != null)
            requiredOffer.setStatus(updateOfferDto.getOfferStatus());
        if(updateOfferDto.getDescription() != null)
            requiredOffer.setDescription(updateOfferDto.getDescription());
        if(updateOfferDto.getPrice() != null)
            requiredOffer.setPrice(updateOfferDto.getPrice());
        if(updateOfferDto.getExpirationDate() != null)
            requiredOffer.setExpirationDate(updateOfferDto.getExpirationDate());
        this.offerDao.save(requiredOffer);
        return this.modelMapper.map(requiredOffer,OfferDto.class);
    }

    @Override
    @Transactional
    public OfferDto updateOfferBuyer(UpdateOfferBuyerDto updateOfferBuyerDto) {
        Offer requiredOffer = this.offerDao.findById(updateOfferBuyerDto.getOfferID()).orElseThrow();
        if(requiredOffer.isExpired())
            throw new InvalidFormat("errors.offer.alreadyExpired");
        if(updateOfferBuyerDto.getDescription() != null)
            requiredOffer.setDescription(updateOfferBuyerDto.getDescription());
        if(updateOfferBuyerDto.getPrice() != null) {
            if(updateOfferBuyerDto.getPrice().compareTo(requiredOffer.getProduct().getMinPrice()) < 0)
                throw new InvalidFormat("errors.offers.updateBuyer.invalidPrice");
            requiredOffer.setPrice(updateOfferBuyerDto.getPrice());
        }
        this.offerDao.save(requiredOffer);
        return this.modelMapper.map(requiredOffer,OfferDto.class);
    }

    @Override
    @Transactional
    public OfferDto updateOfferSeller(UpdateOfferSellerDto updateOfferSeller) {
        Product requiredProduct = this.productDao.findById(updateOfferSeller.getProductID()).orElseThrow();
        Offer requiredOffer = this.offerDao.findById(updateOfferSeller.getOfferID()).orElseThrow();
        Optional<Offer> offerOptional = this.offerDao.getOfferByStatus(requiredOffer.getId(),OfferStatus.ACCEPTED);
        OfferStatus offerStatus = updateOfferSeller.getOfferStatus();
        if(requiredOffer.isExpired())
            throw new InvalidFormat("errors.offer.alreadyExpired");
        if(!requiredProduct.getSeller().getId().equals(requiredOffer.getProduct().getSeller().getId()))
            throw new InvalidFormat("errors.product.updateSeller.invalidProduct");
        if(offerOptional.isPresent())
            throw new InvalidFormat("errors.offer.updateSeller.alreadyAccepted");
        if(offerStatus != null) {
            if(requiredOffer.getStatus() != OfferStatus.OPEN)
                throw new InvalidFormat("errors.offer.updateSeller.invalidOldStatus");
            if(offerStatus.equals(OfferStatus.EXPIRED) || offerStatus.equals(OfferStatus.OPEN))
                throw new InvalidFormat("errors.offer.updateSeller.invalidStatus");
            requiredOffer.setStatus(updateOfferSeller.getOfferStatus());
        }
        this.offerDao.save(requiredOffer);
        return this.modelMapper.map(requiredOffer,OfferDto.class);
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 60 * 1000)
    public void handleExpiredOffers() {
        List<Offer> expiredOffers = this.offerDao.getOffersByDate(LocalDate.now());
        expiredOffers.forEach(offer -> {
            log.info(String.format("Offer [%s] has expired at [%s]",offer.getId().toString(),this.dateTimeFormatter.format(LocalDateTime.now())));
            offer.setExpired(true);
            offer.setStatus(OfferStatus.EXPIRED);
            this.offerDao.save(offer);
        });
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 24 * 5 * 60 * 60 * 1000,initialDelay = 24 * 60 * 5000)
    public void deleteExpiredOffers() {
        List<Offer> expiredOffers = this.offerDao.getExpiredOffers(true);
        this.offerDao.deleteAll(expiredOffers);
        log.info(String.format("Expired offers have been deleted at [%s]",this.dateTimeFormatter.format(LocalDateTime.now())));
    }

    @Override
    public OfferStatus[] getStatuses() {
        return OfferStatus.values();
    }

    @Override
    @Transactional
    public void deleteOffer(UUID offerID) {
        this.offerDao.findById(offerID).orElseThrow();
        this.offerDao.deleteById(offerID);
    }
}
