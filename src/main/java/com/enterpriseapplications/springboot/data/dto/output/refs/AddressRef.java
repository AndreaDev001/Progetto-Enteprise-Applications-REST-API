package com.enterpriseapplications.springboot.data.dto.output.refs;

import com.enterpriseapplications.springboot.controllers.AddressController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.Address;
import com.enterpriseapplications.springboot.data.entities.enums.addresses.CountryCode;
import lombok.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRef extends GenericOutput<AddressRef>
{
    private UUID id;
    private CountryCode code;
    private String locality;

    @Override
    public void addLinks(Object... params) {
        super.addLinks(params);
        this.add(linkTo(methodOn(AddressController.class).getAddress(id)).withRel("details").withName("details"));
    }
    public AddressRef(Address address) {
        this.id = address.getId();
        this.code = address.getCountryCode();
        this.locality = address.getLocality();
    }
}
