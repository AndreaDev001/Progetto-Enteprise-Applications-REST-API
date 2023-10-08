package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class OfferServiceImpTest {

    boolean valid(Offer offer, OfferDto offerDto) {
        Assert.assertNotNull(offerDto);
        Assert.assertEquals(offer.getId(),offerDto.getId());
        Assert.assertEquals(offer.getDescription(),offerDto.getDescription());
        Assert.assertEquals(offer.getPrice(),offerDto.getPrice());
        Assert.assertEquals(offer.getStatus(),offerDto.getStatus());
        Assert.assertEquals(offer.getBuyer().getId(),offerDto.getBuyer().getId());
        Assert.assertEquals(offer.getProduct().getId(),offerDto.getProduct().getId());
        Assert.assertEquals(offer.getExpirationDate(),offerDto.getExpirationDate());
        Assert.assertEquals(offer.isExpired(),offerDto.isExpired());
        Assert.assertEquals(offer.getCreatedDate(),offerDto.getCreatedDate());
        return true;
    }

    @Test
    void getOffer() {
    }

    @Test
    void getOffers() {
    }

    @Test
    void getOffersByStatus() {
    }

    @Test
    void getOffersByExpired() {
    }

    @Test
    void getOffersByBuyerID() {
    }

    @Test
    void getOffersByProductID() {
    }

    @Test
    void getOffersByProductIDAndStatus() {
    }

    @Test
    void getOffersBySpec() {
    }

    @Test
    void createOffer() {
    }
}