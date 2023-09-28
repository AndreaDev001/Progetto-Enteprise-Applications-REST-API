package com.enterpriseapplications.springboot.data.entities.images;

import com.enterpriseapplications.springboot.data.entities.Product;
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
        this.setProduct(product);
    }
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
