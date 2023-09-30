package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService
{
    PagedModel<ImageDto> getImages(Pageable pageable);
    ImageDto getImage(UUID imageID);
    List<ImageDto> getImagesByName(String name);
    List<ImageDto> getImagesByType(String type);
    ImageOwner getImageOwners();
    void deleteImage(UUID imageID);
}
