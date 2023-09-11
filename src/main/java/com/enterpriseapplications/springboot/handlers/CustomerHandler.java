package com.enterpriseapplications.springboot.handlers;


import com.enterpriseapplications.springboot.data.entities.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.springframework.stereotype.Component;

@Component
public class CustomerHandler
{
    public String createCustomer(User user) throws StripeException {
        CustomerCreateParams.Builder customerCreateParams = CustomerCreateParams.builder();
        customerCreateParams.setEmail(user.getEmail());
        customerCreateParams.setDescription(user.getDescription());
        customerCreateParams.setTaxExempt(CustomerCreateParams.TaxExempt.EXEMPT);
        customerCreateParams.setName(user.getName() + " " + user.getSurname());
        CustomerCreateParams params = customerCreateParams.build();
        Customer customer = Customer.create(params);
        return customer.getId();
    }
    public String getCustomer(User user) throws StripeException {
        Customer customer = Customer.retrieve(user.getStripeID());
        return customer.getId();
    }
}
