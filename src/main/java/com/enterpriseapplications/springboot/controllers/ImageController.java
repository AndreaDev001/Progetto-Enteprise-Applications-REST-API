package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.input.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.ImageDto;
import com.enterpriseapplications.springboot.services.interfaces.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController
{
    private final ImageService imageService;

    @GetMapping("{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
        ImageDto imageDto = this.imageService.getImage(name);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageDto.getType())).body(imageDto.getImage());
    }

    @PostMapping(value = "/user",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> uploadUserImage(@ModelAttribute @Valid CreateUserImageDto userImageDto) {
        return ResponseEntity.ok(this.imageService.uploadUserImage(userImageDto.getOwnerID(),userImageDto.getFile()));
    }

    @PostMapping(value = "/product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageDto>> uploadProductImages(@ModelAttribute @Valid CreateProductImageDto productImageDto) {
        return ResponseEntity.ok(this.imageService.uploadProductImages(productImageDto.getOwnerID(),productImageDto.getFiles()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long imageID) {
        this.imageService.deleteImage(imageID);
        return ResponseEntity.noContent().build();
    }
}
