package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.images.ProductImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductImageServiceImpTest {

    private ProductImageServiceImp productImageServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<ProductImage> pagedResourcesAssembler;
    private ProductImage firstProductImage;
    private ProductImage secondProductImage;

    @Mock
    private ProductDao productDao;
    @Mock
    private ProductImageDao productImageDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        productImageServiceImp = new ProductImageServiceImp(productDao,productImageDao,modelMapper,pagedResourcesAssembler);
        List<Image> images = ImageServiceImpTest.createImages();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        firstProductImage = new ProductImage(images.get(0));
        secondProductImage = new ProductImage(images.get(1));
        firstProductImage.setProduct(firstProduct);
        secondProductImage.setProduct(secondProduct);
    }

    boolean valid(ProductImage productImage, ProductImageDto productImageDto) {
        Assert.assertTrue(ImageServiceImpTest.valid(productImage,productImageDto));
        Assert.assertEquals(productImage.getProduct().getId(),productImageDto.getProduct().getId());
        return true;
    }

    @Test
    void getImage() {
        given(this.productImageDao.findById(firstProductImage.getId())).willReturn(Optional.of(firstProductImage));
        given(this.productImageDao.findById(secondProductImage.getId())).willReturn(Optional.of(secondProductImage));
        ProductImageDto firstImage = this.productImageServiceImp.getProductImage(firstProductImage.getId());
        ProductImageDto secondImage = this.productImageServiceImp.getProductImage(secondProductImage.getId());
        Assert.assertTrue(valid(this.firstProductImage,firstImage));
        Assert.assertTrue(valid(this.secondProductImage,secondImage));
    }
    @Test
    void getImages() {
    }

    @Test
    void getProductImages() {
    }

    @Test
    void uploadImages() {
    }

    @Test
    void getFirstProductImage() {
    }

    @Test
    void getLastProductImage() {
    }

    @Test
    void getProductImage() {
    }
}