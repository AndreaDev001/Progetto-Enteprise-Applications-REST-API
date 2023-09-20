package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService
{
    Page<ProductDto> getProductsBySeller(Long sellerID, Pageable pageable);
    ProductDto getProductDetails(Long productID);
    ProductDto updateProduct(UpdateProductDto updateProductDto);
    void deleteProduct(Long productID);
}
