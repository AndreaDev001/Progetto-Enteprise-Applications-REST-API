package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserImageService
{
    UserImageDto getUserImageDetails(UUID userID);
    UserImageDto getUserImage(UUID userID);
    UserImageDto uploadImage(CreateUserImageDto createUserImageDto);
}
