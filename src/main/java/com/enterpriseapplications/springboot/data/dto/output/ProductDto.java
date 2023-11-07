package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.config.hateoas.HateoasUtils;
import com.enterpriseapplications.springboot.controllers.ConversationController;
import com.enterpriseapplications.springboot.controllers.LikeController;
import com.enterpriseapplications.springboot.controllers.images.ProductImageController;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import lombok.*;
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
@Builder
@Relation(collectionRelation = "content")
public class ProductDto extends GenericOutput<ProductDto>
{
    private UUID id;
    private String name;
    private String description;
    private String brand;
    private UserRef seller;
    private ProductCondition condition;
    private ProductVisibility visibility;
    private ProductStatus status;
    private BigDecimal price;
    private BigDecimal minPrice;
    private CategoryDto category;
    private int amountOfLikes = 0;
    private LocalDate createdDate;

    @Override
    @SneakyThrows
    public void addLinks(Object... params) {
        PaginationRequest paginationRequest = new PaginationRequest(0,20);
        String paginationQuery = HateoasUtils.convert(paginationRequest);
        this.add(linkTo(methodOn(ProductImageController.class).getProductImages(id)).withRel("images-all").withName("all"));
        this.add(linkTo(methodOn(ProductImageController.class).getFirstImage(id)).withRel("images-first").withName("first"));
        this.add(linkTo(methodOn(ProductImageController.class).getLastImage(id)).withRel("images-last").withName("last"));
        this.add(linkTo(methodOn(LikeController.class).getLikesByProduct(id,paginationRequest)).slash(paginationQuery).withRel("received_likes").withName("receivedLikes"));
    }
}
