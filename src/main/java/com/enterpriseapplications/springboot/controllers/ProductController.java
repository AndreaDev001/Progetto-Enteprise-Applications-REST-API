package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.services.interfaces.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProductController
{
    private final ProductService productService;


    @GetMapping
    public ResponseEntity<PagedModel<ProductDto>> getProducts(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProducts(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("{productID}/details")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productService.getProductDetails(productID));
    }

    @GetMapping("seller/{sellerID}")
    public ResponseEntity<PagedModel<ProductDto>> getProducts(@PathVariable("sellerID") UUID sellerID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProductsBySeller(sellerID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/spec")
    public ResponseEntity<PagedModel<ProductDto>> getProductsBySpec(@ParameterObject @Valid ProductSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductDto> products = this.productService.getProductsBySpec(ProductSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("conditions")
    public ResponseEntity<ProductCondition[]> getConditions() {
        return ResponseEntity.ok(this.productService.getConditions());
    }

    @GetMapping("visibilities")
    public ResponseEntity<ProductVisibility[]> getVisibilities() {
        return ResponseEntity.ok(this.productService.getVisibilities());
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid UpdateProductDto updateProductDto) {
        return ResponseEntity.ok(this.productService.updateProduct(updateProductDto));
    }

    @DeleteMapping("{productID}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productID") UUID productID) {
        this.productService.deleteProduct(productID);
        return ResponseEntity.noContent().build();
    }
}
