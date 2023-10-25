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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/private/{id}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductImageDto> getImage(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(this.productImageService.getProductImage(id));
    }

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ProductImageDto>> getProductImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductImageDto> products = this.productImageService.getImages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/public/{productID}")
    public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productImageService.getProductImages(productID));
    }
    @GetMapping("/public/{productID}/first")
    public ResponseEntity<byte[]> getFirstImage(@PathVariable("productID") UUID productID) {
        ProductImageDto productImageDto = this.productImageService.getFirstProductImage(productID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType().getName())).body(productImageDto.getImage());
    }
    @GetMapping("/public/{productID}/amount")
    public ResponseEntity<Integer> getAmount(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productImageService.getAmount(productID));
    }
    @GetMapping("/public/{productID}/last")
    public ResponseEntity<byte[]> getLastImage(@PathVariable("productID") UUID productID) {
        ProductImageDto productImageDto = this.productImageService.getLastProductImage(productID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType().getName())).body(productImageDto.getImage());
    }
    @GetMapping("/public/{productID}/{index}")
    public ResponseEntity<byte[]> getImageByIndex(@PathVariable("productID") UUID productID,@PathVariable("index") Integer index) {
        ProductImageDto productImageDto = this.productImageService.getProductImage(productID,index);
        return ResponseEntity.ok().contentType(MediaType.valueOf(productImageDto.getType().getName())).body(productImageDto.getImage());
    }
    @PostMapping(value = "/private",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<List<ProductImageDto>> uploadProductImages(@ModelAttribute @Valid CreateProductImageDto productImageDto) {
        return ResponseEntity.ok(this.productImageService.uploadImages(productImageDto));
    }
    @DeleteMapping("/private/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<Void> deleteProductImages(@PathVariable("productID") UUID productID) {
        this.productImageService.deleteProductImages(productID);
        return ResponseEntity.noContent().build();
    }
}
