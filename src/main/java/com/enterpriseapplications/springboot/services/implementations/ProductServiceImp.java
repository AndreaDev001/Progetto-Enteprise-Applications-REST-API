package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.services.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp extends GenericServiceImp<Product,ProductDto> implements ProductService
{
    private final UserDao userDao;
    private final ProductDao productDao;
    private final CategoryDao categoryDao;


    public ProductServiceImp(ProductDao productDao,UserDao userDao,CategoryDao categoryDao,ModelMapper modelMapper,PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        super(modelMapper,Product.class,ProductDto.class,pagedResourcesAssembler);
        this.productDao = productDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    @Override
    @Cacheable(value = {CacheConfig.CACHE_ALL_PRODUCTS})
    public PagedModel<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = this.productDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public ProductSpecifications.OrderType[] getOrderTypes() {
        return ProductSpecifications.OrderType.values();
    }

    @Override
    public PagedModel<ProductDto> getProductsBySeller(UUID sellerID, Pageable pageable) {
        Page<Product> products = this.productDao.getProductsBySeller(sellerID,ProductVisibility.PUBLIC,pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public ProductDto getProductDetails(UUID productID) {
        Product product = this.productDao.findById(productID).orElseThrow();
        ProductDto productDto = this.modelMapper.map(product,ProductDto.class);
        productDto.addLinks();
        return productDto;
    }

    @Override
    @CacheEvict(value = {CacheConfig.CACHE_ALL_PRODUCTS,CacheConfig.CACHE_SEARCH_PRODUCTS},allEntries = true)
    public ProductDto createProduct(CreateProductDto createProductDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Category requiredCategory = this.categoryDao.getCategory(createProductDto.getPrimaryCat(), createProductDto.getSecondaryCat(), createProductDto.getTertiaryCat()).orElseThrow();
        Product requiredProduct = buildProduct(createProductDto, requiredCategory, requiredUser);
        requiredProduct = this.productDao.save(requiredProduct);
        ProductDto productDto = this.modelMapper.map(requiredProduct, ProductDto.class);
        productDto.addLinks();
        return productDto;
    }

    private Product buildProduct(CreateProductDto createProductDto, Category requiredCategory, User requiredUser) {
        Product requiredProduct = new Product();
        if(createProductDto.getPrice().compareTo(createProductDto.getMinPrice()) < 0)
            throw new InvalidFormat("errors.product.invalidMinPrice");
        requiredProduct.setName(createProductDto.getName());
        requiredProduct.setDescription(createProductDto.getDescription());
        requiredProduct.setBrand(createProductDto.getBrand());
        requiredProduct.setCondition(createProductDto.getCondition());
        requiredProduct.setVisibility(createProductDto.getVisibility());
        requiredProduct.setPrice(createProductDto.getPrice());
        requiredProduct.setMinPrice(createProductDto.getMinPrice());
        requiredProduct.setCategory(requiredCategory);
        requiredProduct.setSeller(requiredUser);
        requiredProduct.setStatus(ProductStatus.AVAILABLE);
        return requiredProduct;
    }

    @Override
    public PagedModel<ProductDto> getProductsBySpec(Specification<Product> specification, Pageable pageable) {
        Page<Product> products = this.productDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public PagedModel<ProductDto> getRecentlyCreatedProducts(Pageable pageable) {
        Page<Product> products = this.productDao.getRecentlyCreatedProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public PagedModel<ProductDto> getMostLikedProducts(Pageable pageable) {
        Page<Product> products = this.productDao.getMostLikedProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public PagedModel<ProductDto> getMostExpensiveProducts(Pageable pageable) {
        Page<Product> products = this.productDao.getMostExpensiveProducts(ProductVisibility.PUBLIC,ProductStatus.AVAILABLE,pageable);
        return this.pagedResourcesAssembler.toModel(products,modelAssembler);
    }

    @Override
    public PagedModel<ProductDto> getSimilarProducts(UUID productID, Pageable pageable) {
        Product requiredProduct = this.productDao.findById(productID).orElseThrow();
        ProductSpecifications.Filter filter = new ProductSpecifications.Filter(SpecificationsUtils.OrderMode.DESCENDED,requiredProduct);
        Page<Product> products = this.productDao.findAll(ProductSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(products,this.modelAssembler);
    }

    @Override
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_PRODUCTS,CacheConfig.CACHE_ALL_PRODUCTS},allEntries = true)
    public ProductDto updateProduct(UpdateProductDto updateProductDto) {
        Product requiredProduct =  this.productDao.findById(updateProductDto.getProductID()).orElseThrow();
        if(updateProductDto.getDescription() != null)
            requiredProduct.setDescription(updateProductDto.getDescription());
        if(updateProductDto.getBrand() != null)
            requiredProduct.setBrand(updateProductDto.getBrand());
        if(updateProductDto.getCondition() != null)
            requiredProduct.setCondition(updateProductDto.getCondition());
        if(updateProductDto.getVisibility() != null)
            requiredProduct.setVisibility(updateProductDto.getVisibility());
        this.productDao.save(requiredProduct);
        return this.modelMapper.map(requiredProduct,ProductDto.class);
    }

    @Override
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_PRODUCTS,CacheConfig.CACHE_ALL_PRODUCTS},allEntries = true)
    public void deleteProduct(UUID productID) {
        this.productDao.findById(productID);
        this.productDao.deleteById(productID);
    }

    @Override
    public ProductStatus[] getStatues() {
        return ProductStatus.values();
    }

    @Override
    public ProductCondition[] getConditions() {
        return ProductCondition.values();
    }

    @Override
    public ProductVisibility[] getVisibilities() {
        return ProductVisibility.values();
    }
}
