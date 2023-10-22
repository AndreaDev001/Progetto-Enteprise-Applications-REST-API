package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductImageService
{
    ProductImageDto getProductImage(UUID id);
    PagedModel<ProductImageDto> getImages(Pageable pageable);
    List<ProductImageDto> getProductImages(UUID productID);
    List<ProductImageDto> uploadImages(CreateProductImageDto createProductImageDto);
    Integer getAmount(UUID productID);
    ProductImageDto getFirstProductImage(UUID productID);
    ProductImageDto getLastProductImage(UUID productID);
    ProductImageDto getProductImage(UUID productID,Integer index);
    void deleteProductImages(UUID productID);
}
