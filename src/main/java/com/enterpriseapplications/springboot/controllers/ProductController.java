package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.services.interfaces.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProductController
{
    private final ProductService productService;
    
    
    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ProductDto>> getProducts(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProducts(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/{productID}/details")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productService.getProductDetails(productID));
    }

    @GetMapping("/public/{productID}/similar")
    public ResponseEntity<PagedModel<ProductDto>> getSimilarProducts(@PathVariable("productID") UUID productID,@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getSimilarProducts(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/seller/{sellerID}")
    public ResponseEntity<PagedModel<ProductDto>> getProducts(@PathVariable("sellerID") UUID sellerID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProductsBySeller(sellerID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/spec")
    @Cacheable(value = CacheConfig.CACHE_SEARCH_PRODUCTS,key = "{#filter.toString(),#paginationRequest.toString()}")
    public ResponseEntity<PagedModel<ProductDto>> getProductsBySpec(@ParameterObject @Valid ProductSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProductsBySpec(ProductSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/created")
    public ResponseEntity<PagedModel<ProductDto>> getMostRecentlyCreatedProducts(@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getRecentlyCreatedProducts(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/liked")
    public ResponseEntity<PagedModel<ProductDto>> getMostLikedProducts(@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getMostLikedProducts(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/expensive")
    public ResponseEntity<PagedModel<ProductDto>> getMostExpensiveProducts(@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getMostExpensiveProducts(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/conditions")
    public ResponseEntity<ProductCondition[]> getConditions() {
        return ResponseEntity.ok(this.productService.getConditions());
    }

    @GetMapping("/public/visibilities")
    public ResponseEntity<ProductVisibility[]> getVisibilities() {
        return ResponseEntity.ok(this.productService.getVisibilities());
    }

    @GetMapping("/public/statues")
    public ResponseEntity<ProductStatus[]> getStatues() {
        return ResponseEntity.ok(this.productService.getStatues());
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<ProductSpecifications.OrderType[]> getOrderTypes() {
        return ResponseEntity.ok(this.productService.getOrderTypes());
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(this.productService.createProduct(createProductDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#updateProductDto.productID)")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid UpdateProductDto updateProductDto) {
        return ResponseEntity.ok(this.productService.updateProduct(updateProductDto));
    }

    @DeleteMapping("/private/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productID") UUID productID) {
        this.productService.deleteProduct(productID);
        return ResponseEntity.noContent().build();
    }
}
