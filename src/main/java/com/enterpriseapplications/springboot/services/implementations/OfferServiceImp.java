package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.OfferDao;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImp implements OfferService
{
    private final OfferDao offerDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<OfferDto> getOffersByStatus(OfferStatus offerStatus, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByStatus(offerStatus,pageable);
        return new PageImpl<>(offers.stream().map(offer -> this.modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList()),pageable,offers.getTotalElements());
    }

    @Override
    public Page<OfferDto> getOffersByExpired(boolean expired, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByExpired(expired,pageable);
        return new PageImpl<>(offers.stream().map(offer -> this.modelMapper.map(offer,OfferDto.class)).collect(Collectors.toList()),pageable,offers.getTotalElements());
    }

    @Override
    public Page<OfferDto> getOffersByBuyerID(Long buyerID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByBuyerID(buyerID,pageable);
        return new PageImpl<>(offers.stream().map(offer -> this.modelMapper.map(offer,OfferDto.class)).collect(Collectors.toList()),pageable,offers.getTotalElements());
    }

    @Override
    public Page<OfferDto> getOffersByProductID(Long productID, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByProductID(productID,pageable);
        return new PageImpl<>(offers.stream().map(offer -> this.modelMapper.map(offer,OfferDto.class)).collect(Collectors.toList()),pageable,offers.getTotalElements());
    }

    @Override
    public Page<OfferDto> getOffersByProductIDAndStatus(Long productID, OfferStatus status, Pageable pageable) {
        Page<Offer> offers = this.offerDao.getOffersByProductAndStatus(productID,status,pageable);
        return new PageImpl<>(offers.stream().map(offer -> this.modelMapper.map(offer,OfferDto.class)).collect(Collectors.toList()),pageable,offers.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteOffer(Long offerID) {
        this.offerDao.findById(offerID).orElseThrow();
        this.offerDao.deleteById(offerID);
    }
}
