package com.enterpriseapplications.springboot.data.dto.output.refs;

import com.enterpriseapplications.springboot.controllers.PaymentMethodController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethodRef extends GenericOutput<PaymentMethodRef>
{
    private UUID id;
    private PaymentMethodBrand brand;
    private String holderName;


    public PaymentMethodRef(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.brand = paymentMethod.getBrand();
        this.holderName = paymentMethod.getHolderName();
    }

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(PaymentMethodController.class).getPaymentMethods(id)).withRel("details").withName("details"));
    }
}
