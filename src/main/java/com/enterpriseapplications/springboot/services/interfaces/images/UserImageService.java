package com.enterpriseapplications.springboot.services.interfaces.images;

import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService
{
    UserImageDto getUserImageDetails(Long userID);
    UserImageDto getUserImage(Long userID);
    UserImageDto uploadImage(CreateUserImageDto createUserImageDto);
}
