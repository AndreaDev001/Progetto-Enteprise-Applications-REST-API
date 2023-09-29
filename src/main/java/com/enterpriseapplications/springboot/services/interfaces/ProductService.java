package com.enterpriseapplications.springboot.services.interfaces;

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
    ProductDto getProductDetails(UUID productID);
    ProductDto updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(UUID productID);
    ProductCondition[] getConditions();
    ProductVisibility[] getVisibilities();
}
