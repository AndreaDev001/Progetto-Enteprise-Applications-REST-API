package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductImageService
{
    List<ProductImageDto> getProductImages(UUID productID);
    List<ProductImageDto> uploadImages(CreateProductImageDto createProductImageDto);
    ProductImageDto getFirstProductImage(UUID productID);
    ProductImageDto getLastProductImage(UUID productID);
    ProductImageDto getProductImage(UUID productID,Integer index);
    void deleteProductImages(UUID productID);
}
