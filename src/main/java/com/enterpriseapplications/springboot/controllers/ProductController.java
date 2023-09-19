package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.services.interfaces.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProductController
{
    private final ProductService productService;


    @GetMapping("{productID}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("productID") Long productID) {
        return ResponseEntity.ok(this.productService.getProductDetails(productID));
    }

    @GetMapping("seller/{sellerID}")
    public ResponseEntity<PaginationResponse<ProductDto>> getProducts(@PathVariable("sellerID") Long sellerID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ProductDto> products = this.productService.getProductsBySeller(sellerID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(products.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),products.getTotalPages(),products.getTotalElements()));
    }

    @DeleteMapping("{productID}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productID") Long productID) {
        this.productService.deleteProduct(productID);
        return ResponseEntity.noContent().build();
    }
}
