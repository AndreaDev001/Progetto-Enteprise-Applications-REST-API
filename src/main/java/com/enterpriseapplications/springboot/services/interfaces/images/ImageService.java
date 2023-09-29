package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService
{
    ImageDto getImage(UUID imageID);
    List<ImageDto> getImagesByName(String name);
    List<ImageDto> getImagesByType(String type);
    void deleteImage(UUID imageID);
}
