package com.enterpriseapplications.springboot.data.entities.images;

import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
    public ProductImage(Product product,MultipartFile multipartFile) throws IOException {
        super(multipartFile);
        this.setOwner(ImageOwner.PRODUCT);
        this.setProduct(product);
    }
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
