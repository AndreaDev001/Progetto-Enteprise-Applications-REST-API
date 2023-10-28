package com.enterpriseapplications.springboot.data.entities.images;

import com.enterpriseapplications.springboot.data.entities.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Table(name = "PRODUCT_IMAGES")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductImage extends Image
{
    public ProductImage(Image image) {
        this.setId(image.getId());
        this.setType(image.getType());
        this.setImage(image.getImage());
        this.setCreatedDate(image.getCreatedDate());
        this.setLastModifiedDate(image.getLastModifiedDate());
    }
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
