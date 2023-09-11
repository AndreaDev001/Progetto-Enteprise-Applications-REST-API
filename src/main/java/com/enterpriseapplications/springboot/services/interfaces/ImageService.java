package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService
{
    ImageDto getImage(String name);
    List<ImageDto> getImagesByType(String type);
    ImageDto uploadUserImage(Long userID,MultipartFile file);
    List<ImageDto> uploadProductImages(Long productID,List<MultipartFile> file);
    void deleteImage(Long imageID);
}
