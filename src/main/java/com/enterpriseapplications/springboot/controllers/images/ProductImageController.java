package com.enterpriseapplications.springboot.controllers.images;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.services.interfaces.images.ProductImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productImages")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ProductImageController
{
    private final ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<PagedModel<ProductImageDto>> getProductImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductImageDto> products = this.productImageService.getImages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("{productID}")
    public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productImageService.getProductImages(productID));
    }
    @GetMapping("{productID}/first")
    public ResponseEntity<byte[]> getFirstImage(@PathVariable("productID") UUID productID) {
        ProductImageDto productImageDto = this.productImageService.getFirstProductImage(productID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType())).body(productImageDto.getImage());
    }
    @GetMapping("{productID}/last")
    public ResponseEntity<byte[]> getLastImage(@PathVariable("productID") UUID productID) {
        ProductImageDto productImageDto = this.productImageService.getLastProductImage(productID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType())).body(productImageDto.getImage());
    }
    @GetMapping("{productID}/{index}")
    public ResponseEntity<byte[]> getImageByIndex(@PathVariable("productID") UUID productID,@PathVariable("index") Integer index) {
        ProductImageDto productImageDto = this.productImageService.getProductImage(productID,index);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType())).body(productImageDto.getImage());
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProductImageDto>> uploadProductImages(@ModelAttribute @Valid CreateProductImageDto productImageDto) {
        return ResponseEntity.ok(this.productImageService.uploadImages(productImageDto));
    }
    @DeleteMapping("{productID}")
    public ResponseEntity<Void> deleteProductImages(@PathVariable("productID") UUID productID) {
        this.productImageService.deleteProductImages(productID);
        return ResponseEntity.noContent().build();
    }
}
