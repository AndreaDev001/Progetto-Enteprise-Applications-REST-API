package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface ProductService
{
    PagedModel<ProductDto> getProductsBySeller(Long sellerID, Pageable pageable);
    ProductDto getProductDetails(Long productID);
    ProductDto updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(Long productID);
    ProductCondition[] getConditions();
    ProductVisibility[] getVisibilities();
}
