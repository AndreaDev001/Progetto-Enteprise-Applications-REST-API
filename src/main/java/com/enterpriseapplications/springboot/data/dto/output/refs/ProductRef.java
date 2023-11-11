package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.ProductController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
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
    private String description;
    private String brand;
    private ProductStatus status;
    private BigDecimal price;
    private UserRef seller;
    private Integer likes;

    public ProductRef(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setDescription(product.getDescription());
        this.setBrand(product.getBrand());
        this.setPrice(product.getPrice());
        this.setStatus(product.getStatus());
        this.setLikes(product.getReceivedLikes().size());
        this.setSeller(new UserRef(product.getSeller()));
        this.addLinks();
    }

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ProductController.class).getProductDetails(id)).withRel("details"));
    }
}
