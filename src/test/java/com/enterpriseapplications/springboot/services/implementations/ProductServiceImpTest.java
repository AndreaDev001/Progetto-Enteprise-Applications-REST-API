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
import com.enterpriseapplications.springboot.services.GenericTestImp;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest extends GenericTestImp<Product,ProductDto> {

    private ProductServiceImp productServiceImp;

    @Mock
    public UserDao userDao;
    @Mock
    public ProductDao productDao;
    @Mock
    public CategoryDao categoryDao;


    @Override
    protected void init() {
        super.init();
        this.productServiceImp = new ProductServiceImp(productDao,userDao,categoryDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Category category = Category.builder().id(UUID.randomUUID()).build();
        firstElement = Product.builder().id(UUID.randomUUID()).category(category).name("name").description("description").brand("brand").condition(ProductCondition.NEW).visibility(ProductVisibility.PUBLIC).seller(firstUser).price(BigDecimal.valueOf(100)).build();
        secondElement = Product.builder().id(UUID.randomUUID()).category(category).name("name").description("description").brand("brand").condition(ProductCondition.ALMOST_NEW).visibility(ProductVisibility.PRIVATE).seller(secondUser).price(BigDecimal.valueOf(200)).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    public boolean valid(Product product, ProductDto productDto) {
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
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
    }

    @Test
    void getProductsBySeller() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getProductsBySeller(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getProductsBySeller(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
    }

    @Test
    void getProductDetails() {
        given(this.productDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.productDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        ProductDto firstProductDto = this.productServiceImp.getProductDetails(firstElement.getId());
        ProductDto secondProductDto = this.productServiceImp.getProductDetails(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstProductDto));
        Assert.assertTrue(valid(secondElement,secondProductDto));
    }

    @Test
    void getRecentlyCreatedProducts() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getRecentlyCreatedProducts(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getRecentlyCreatedProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
    }

    @Test
    void getMostLikedProducts() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getMostLikedProducts(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getMostLikedProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
    }

    @Test
    void getProductsBySpec() {

    }

    @Test
    void updateProduct() {
    }
}