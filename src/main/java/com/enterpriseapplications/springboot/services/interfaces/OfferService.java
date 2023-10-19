package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface OfferService
{
    OfferDto getOffer(UUID offerID);
    PagedModel<OfferDto> getOffers(Pageable pageable);
    PagedModel<OfferDto> getCreatedOffers(UUID userID,Pageable pageable);
    PagedModel<OfferDto> getReceivedOffers(UUID userID,Pageable pageable);
    PagedModel<OfferDto> getOffersByStatus(OfferStatus offerStatus, Pageable pageable);
    PagedModel<OfferDto> getOffersByExpired(boolean expired,Pageable pageable);
    PagedModel<OfferDto> getOffersByBuyerID(UUID buyerID, Pageable pageable);
    PagedModel<OfferDto> getOffersByProductID(UUID productID,Pageable pageable);
    PagedModel<OfferDto> getOffersByProductIDAndStatus(UUID productID,OfferStatus status,Pageable pageable);
    PagedModel<OfferDto> getOffersBySpec(Specification<Offer> specification, Pageable pageable);
    OfferDto createOffer(CreateOfferDto createOfferDto);
    void handleExpiredOffers();
    void deleteExpiredOffers();
    OfferStatus[] getStatuses();
    OfferSpecifications.OrderType[] getOrderTypes();
    void deleteOffer(UUID offerID);
}
