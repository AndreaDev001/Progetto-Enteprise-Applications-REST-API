package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.ProductImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import com.enterpriseapplications.springboot.services.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductImageServiceImpTest extends GenericTestImp<ProductImage,ProductImageDto>
{
    private ProductImageServiceImp productImageServiceImp;
    @Mock
    private ProductDao productDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductImageDao productImageDao;


    @Override
    protected void init() {
        super.init();
        productImageServiceImp = new ProductImageServiceImp(userDao,productDao,productImageDao,modelMapper,pagedResourcesAssembler);
        List<Image> images = ImageServiceImpTest.createImages();
        User firstSeller = User.builder().id(UUID.randomUUID()).build();
        User secondSeller = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).seller(firstSeller).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).seller(secondSeller).build();
        TestUtils.generateValues(firstSeller);
        TestUtils.generateValues(secondSeller);
        TestUtils.generateValues(firstProduct);
        TestUtils.generateValues(secondProduct);
        firstElement = new ProductImage(images.get(0));
        secondElement = new ProductImage(images.get(1));
        firstElement.setProduct(firstProduct);
        secondElement.setProduct(secondProduct);
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }

    public boolean valid(ProductImage productImage, ProductImageDto productImageDto) {
        Assert.assertTrue(ImageServiceImpTest.baseValid(productImage,productImageDto));
        Assert.assertEquals(productImage.getProduct().getId(),productImageDto.getProduct().getId());
        return true;
    }

    @Test
    void getProductImage() {
        given(this.productImageDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.productImageDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        ProductImageDto firstImage = this.productImageServiceImp.getProductImage(firstElement.getId());
        ProductImageDto secondImage = this.productImageServiceImp.getProductImage(secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstImage));
        Assert.assertTrue(valid(this.secondElement,secondImage));
    }
    @Test
    void getImages() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productImageDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductImageDto> productImages = this.productImageServiceImp.getImages(pageRequest);
        Assert.assertTrue(compare(elements,productImages.getContent().stream().toList()));
        Assert.assertTrue(validPage(productImages,20,0,1,2));
    }

    @Test
    void getProductImages() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productImageDao.getProductImages(product.getId())).willReturn(elements);
        List<ProductImageDto> productImages = this.productImageServiceImp.getProductImages(product.getId());
        Assert.assertTrue(compare(elements,productImages));
    }

    @Test
    void uploadImages() {

    }

    @Test
    void getFirstProductImage() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productImageDao.getProductImages(product.getId())).willReturn(elements);
        ProductImageDto productImageDto = this.productImageServiceImp.getFirstProductImage(product.getId());
        Assert.assertTrue(valid(firstElement,productImageDto));
    }

    @Test
    void getLastProductImage() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productImageDao.getProductImages(product.getId())).willReturn(elements);
        ProductImageDto productImageDto = this.productImageServiceImp.getLastProductImage(product.getId());
        Assert.assertTrue(valid(secondElement,productImageDto));
    }

    @Test
    void getProductImageIndex() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productImageDao.getProductImages(product.getId())).willReturn(elements);
        List<ProductImageDto> productImages = this.productImageServiceImp.getProductImages(product.getId());
        ProductImageDto first = productImages.get(0);
        ProductImageDto second = productImages.get(1);
        Assert.assertEquals(2, productImages.size());
        Assert.assertTrue(valid(firstElement,first));
        Assert.assertTrue(valid(secondElement,second));
    }
    @Test
    void getProductImagesAmount() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productImageDao.getProductImages(product.getId())).willReturn(elements);
        List<ProductImageDto> productImages = this.productImageServiceImp.getProductImages(product.getId());
        Assert.assertNotNull(productImages);
        Assert.assertEquals(elements.size(),2);
    }
}