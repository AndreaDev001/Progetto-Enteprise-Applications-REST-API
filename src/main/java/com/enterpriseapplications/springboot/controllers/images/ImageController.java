package com.enterpriseapplications.springboot.controllers.images;


import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.services.interfaces.images.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController
{
    private final ImageService imageService;

    @GetMapping("{imageID}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageID") Long imageID) {
        ImageDto imageDto = this.imageService.getImage(imageID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageDto.getType())).body(imageDto.getImage());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ImageDto>> getImagesByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(this.imageService.getImagesByName(name));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ImageDto>> getImagesByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(this.imageService.getImagesByType(type));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long imageID) {
        this.imageService.deleteImage(imageID);
        return ResponseEntity.noContent().build();
    }
}
