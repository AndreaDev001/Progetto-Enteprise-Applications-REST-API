package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.UUID;

public interface PaymentIntentService
{
    PaymentIntent createPaymentIntentProduct(UUID userID, UUID productID) throws StripeException;
    PaymentIntent createPaymentIntentOffer(UUID userID,UUID offerID) throws StripeException;
}
