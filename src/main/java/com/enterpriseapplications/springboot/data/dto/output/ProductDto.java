package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.controllers.images.ProductImageController;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "content")
public class ProductDto extends GenericOutput<ProductDto>
{
    private UUID id;
    private String name;
    private String description;
    private String brand;
    private UserRef seller;
    private ProductCondition condition;
    private BigDecimal price;
    private CategoryDto category;
    private LocalDate createdDate;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(ProductImageController.class).getProductImages(id)).withRel("images-all").withName("all"));
        this.add(linkTo(methodOn(ProductImageController.class).getFirstImage(id)).withRel("images-first").withName("first"));
        this.add(linkTo(methodOn(ProductImageController.class).getLastImage(id)).withRel("images-last").withName("last"));
    }
}
