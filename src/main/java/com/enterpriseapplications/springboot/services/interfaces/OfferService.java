package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService
{
    Page<OfferDto> getOffersByStatus(OfferStatus offerStatus, Pageable pageable);
    Page<OfferDto> getOffersByExpired(boolean expired,Pageable pageable);
    Page<OfferDto> getOffersByBuyerID(Long buyerID,Pageable pageable);
    Page<OfferDto> getOffersByProductID(Long productID,Pageable pageable);
    Page<OfferDto> getOffersByProductIDAndStatus(Long productID,OfferStatus status,Pageable pageable);
    OfferDto createOffer(CreateOfferDto createOfferDto);
    void deleteOffer(Long offerID);
}
