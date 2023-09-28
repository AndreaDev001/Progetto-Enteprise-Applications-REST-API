package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService
{
    List<ProductImageDto> getProductImages(Long productID);
    List<ProductImageDto> uploadImages(CreateProductImageDto createProductImageDto);
    ProductImageDto getFirstProductImage(Long productID);
    ProductImageDto getLastProductImage(Long productID);
    ProductImageDto getProductImage(Long productID,Integer index);
    void deleteProductImages(Long productID);
}
