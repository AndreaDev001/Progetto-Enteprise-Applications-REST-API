package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentIntentService
{
    PaymentIntent createPaymentIntentProduct(Long userID,Long productID) throws StripeException;
    PaymentIntent createPaymentIntentOffer(Long userID,Long offerID) throws StripeException;
}
