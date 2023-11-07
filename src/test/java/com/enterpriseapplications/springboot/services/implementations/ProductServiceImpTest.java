package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import lombok.SneakyThrows;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
    @SneakyThrows
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
        this.defaultBefore();
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
        Assert.assertEquals(product.getReceivedLikes().size(),productDto.getAmountOfLikes());
        return true;
    }

    @Test
    void getProducts() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
        Assert.assertTrue(validPage(products,20,0,1,2));
    }

    @Test
    void getProductsBySeller() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getProductsBySeller(user.getId(),ProductVisibility.PUBLIC,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getProductsBySeller(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
        Assert.assertTrue(validPage(products,20,0,1,2));
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
        given(this.productDao.getRecentlyCreatedProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getRecentlyCreatedProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
        Assert.assertTrue(validPage(products,20,0,1,2));
    }

    @Test
    void getMostLikedProducts() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getMostLikedProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getMostLikedProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
        Assert.assertTrue(validPage(products,20,0,1,2));
    }

    @Test
    void getMostExpensiveProducts() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.getMostExpensiveProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> products = this.productServiceImp.getMostExpensiveProducts(pageRequest);
        Assert.assertTrue(compare(elements,products.getContent().stream().toList()));
        Assert.assertTrue(validPage(products,20,0,1,2));
    }

    @Test
    void getSimilarProducts() {
        Product product = Product.builder().id(UUID.randomUUID()).name("Name").description("Description").build();
        Category category = Category.builder().id(UUID.randomUUID()).primaryCat("Primary").secondaryCat("Secondary").tertiaryCat("Tertiary").build();
        product.setCategory(category);
        ProductSpecifications.Filter filter = new ProductSpecifications.Filter(SpecificationsUtils.OrderMode.DESCENDED,product);
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productDao.findById(product.getId())).willReturn(Optional.of(product));
        given(this.productDao.findAll(ProductSpecifications.withFilter(filter),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> pagedModel = this.productServiceImp.getSimilarProducts(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }
    @Test
    void getProductsBySpec() {
        ProductSpecifications.Filter filter = new ProductSpecifications.Filter();
        PageRequest pageRequest = PageRequest.of(0,20);
        Specification<Product> specification = ProductSpecifications.withFilter(filter);
        given(this.productDao.findAll(specification,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductDto> pagedModel = this.productServiceImp.getProductsBySpec(specification,pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void createProduct() {
        given(this.productDao.save(any())).willReturn(firstElement);
        Category category = Category.builder().id(UUID.randomUUID()).primaryCat("Primary").secondaryCat("Secondary").tertiaryCat("Tertiary").build();
        CreateProductDto createProductDto = CreateProductDto.builder().name("name").description("description").brand("brand").primaryCat("Primary").secondaryCat("Secondary").tertiaryCat("Tertiary").condition(ProductCondition.NEW).visibility(ProductVisibility.PUBLIC).price(new BigDecimal(100)).minPrice(new BigDecimal(20)).build();
        given(this.categoryDao.getCategory(createProductDto.getPrimaryCat(),createProductDto.getSecondaryCat(),createProductDto.getTertiaryCat())).willReturn(Optional.of(category));
        given(this.userDao.findById(this.authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.productDao.save(any())).willReturn(firstElement);
        ProductDto productDto = this.productServiceImp.createProduct(createProductDto);
        Assert.assertTrue(valid(firstElement,productDto));
    }
}