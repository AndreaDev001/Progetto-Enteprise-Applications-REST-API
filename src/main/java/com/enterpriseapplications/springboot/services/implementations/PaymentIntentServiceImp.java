package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.OfferDao;
import com.enterpriseapplications.springboot.data.dao.PaymentMethodDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.PaymentIntentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentIntentServiceImp implements PaymentIntentService
{
    private final UserDao userDao;
    private final ProductDao productDao;
    private final OfferDao offerDao;
    private final PaymentMethodDao paymentMethodDao;

    @Override
    public PaymentIntent createPaymentIntentProduct(Long userID, Long productID) throws StripeException {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        Product requiredProduct = this.productDao.findById(productID).orElseThrow();
        if(requiredUser.getId().equals(requiredProduct.getSeller().getId()))
            throw new InvalidFormat("error.paymentIntents.invalidBuyer");
        PaymentIntentCreateParams paymentIntentCreateParams = this.createPaymentIntent(requiredUser,requiredProduct.getPrice().longValue());
        return PaymentIntent.create(paymentIntentCreateParams);
    }
    @Override
    public PaymentIntent createPaymentIntentOffer(Long userID, Long offerID) throws StripeException {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        Offer requiredOffer = this.offerDao.findById(offerID).orElseThrow();
        if(requiredUser.getId().equals(requiredOffer.getProduct().getSeller().getId()))
            throw new InvalidFormat("error.paymentIntents.invalidBuyer");
        PaymentIntentCreateParams paymentIntentCreateParams = this.createPaymentIntent(requiredUser,requiredOffer.getPrice().longValue());
        return PaymentIntent.create(paymentIntentCreateParams);
    }

    private PaymentIntentCreateParams createPaymentIntent(User requiredUser,Long price) {
        PaymentIntentCreateParams.Builder paymentIntentBuilder = PaymentIntentCreateParams.builder();
        paymentIntentBuilder.addPaymentMethodType("card");
        paymentIntentBuilder.setCustomer(requiredUser.getStripeID());
        paymentIntentBuilder.setAmount(price);
        paymentIntentBuilder.setCurrency("eur");
        paymentIntentBuilder.setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true).build()
        );
        return paymentIntentBuilder.build();
    }
}
