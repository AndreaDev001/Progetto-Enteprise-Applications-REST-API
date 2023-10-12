package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ProductService
{
    PagedModel<ProductDto> getProducts(Pageable pageable);
    PagedModel<ProductDto> getProductsBySeller(UUID sellerID, Pageable pageable);
    PagedModel<ProductDto> getProductsBySpec(Specification<Product> specification, Pageable pageable);
    PagedModel<ProductDto> getRecentlyCreatedProducts(Pageable pageable);
    PagedModel<ProductDto> getMostLikedProducts(Pageable pageable);
    PagedModel<ProductDto> getMostExpensiveProducts(Pageable pageable);
    ProductDto getProductDetails(UUID productID);
    ProductDto createProduct(CreateProductDto createProductDto);
    ProductDto updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(UUID productID);
    ProductCondition[] getConditions();
    ProductVisibility[] getVisibilities();
    ProductSpecifications.OrderType[] getOrderTypes();
}
