package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.ProductController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRef extends GenericOutput<ProductRef>
{
    private UUID id;
    private String name;
    private String brand;
    private BigDecimal price;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ProductController.class).getProductDetails(id)).withRel("details"));
    }
}
