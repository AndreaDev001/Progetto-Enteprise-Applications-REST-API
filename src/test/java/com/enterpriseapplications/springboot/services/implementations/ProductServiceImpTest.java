package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest {

    private ProductServiceImp productServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Product> pagedResourcesAssembler;
    private Product firstProduct;
    private Product secondProduct;


    @Mock
    public UserDao userDao;
    @Mock
    public ProductDao productDao;
    @Mock
    public CategoryDao categoryDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        this.productServiceImp = new ProductServiceImp(productDao,userDao,categoryDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Category category = Category.builder().id(UUID.randomUUID()).build();
        firstProduct = Product.builder().id(UUID.randomUUID()).category(category).name("name").description("description").brand("brand").condition(ProductCondition.NEW).visibility(ProductVisibility.PUBLIC).seller(firstUser).price(BigDecimal.valueOf(100)).build();
        secondProduct = Product.builder().id(UUID.randomUUID()).category(category).name("name").description("description").brand("brand").condition(ProductCondition.ALMOST_NEW).visibility(ProductVisibility.PRIVATE).seller(secondUser).price(BigDecimal.valueOf(200)).build();
    }

    boolean valid(Product product, ProductDto productDto) {
        Assert.assertNotNull(productDto);
        Assert.assertEquals(product.getId(),productDto.getId());
        Assert.assertEquals(product.getName(),productDto.getName());
        Assert.assertEquals(product.getDescription(),productDto.getDescription());
        Assert.assertEquals(product.getBrand(),productDto.getBrand());
        Assert.assertEquals(product.getPrice(),productDto.getPrice());
        Assert.assertEquals(product.getMinPrice(),productDto.getMinPrice());
        Assert.assertEquals(product.getCategory().getId(),productDto.getCategory().getId());
        Assert.assertEquals(product.getCondition(),productDto.getCondition());
        Assert.assertEquals(product.getVisibility(),productDto.getVisibility());
        Assert.assertEquals(product.getLikes().size(),productDto.getAmountOfLikes());
        return true;
    }

    @Test
    void getProducts() {

    }

    @Test
    void getProductsBySeller() {
    }

    @Test
    void getProductDetails() {
        given(this.productDao.findById(firstProduct.getId())).willReturn(Optional.of(firstProduct));
        given(this.productDao.findById(secondProduct.getId())).willReturn(Optional.of(secondProduct));
        ProductDto firstProductDto = this.productServiceImp.getProductDetails(firstProduct.getId());
        ProductDto secondProductDto = this.productServiceImp.getProductDetails(secondProduct.getId());
        Assert.assertTrue(valid(firstProduct,firstProductDto));
        Assert.assertTrue(valid(secondProduct,secondProductDto));
    }

    @Test
    void createProduct() {
    }

    @Test
    void getProductsBySpec() {
    }

    @Test
    void updateProduct() {
    }
}