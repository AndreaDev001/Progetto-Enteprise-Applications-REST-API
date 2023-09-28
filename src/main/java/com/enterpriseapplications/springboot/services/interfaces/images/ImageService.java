package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService
{
    ImageDto getImage(Long imageID);
    List<ImageDto> getImagesByName(String name);
    List<ImageDto> getImagesByType(String type);
    void deleteImage(Long imageID);
}
