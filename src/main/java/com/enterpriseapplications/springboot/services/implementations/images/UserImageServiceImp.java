package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.services.interfaces.images.UserImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Service
public class UserImageServiceImp implements UserImageService
{
    private final UserImageDao userImageDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<UserImage,UserImageDto> modelAssembler;
    private final PagedResourcesAssembler<UserImage> pagedResourcesAssembler;

    public UserImageServiceImp(UserImageDao userImageDao,ModelMapper modelMapper,PagedResourcesAssembler<UserImage> pagedResourcesAssembler) {
        this.userImageDao = userImageDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(UserImage.class,UserImageDto.class,modelMapper);
    }

    @Override
    public PagedModel<UserImageDto> getImages(Pageable pageable) {
        Page<UserImage> userImages = this.userImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(userImages,modelAssembler);
    }

    @Override
    public UserImageDto getUserImageDetails(UUID userID) {
        UserImage userImage = this.userImageDao.getUserImage(userID).orElseThrow();
        UserImageDto userImageDto = this.modelMapper.map(userImage,UserImageDto.class);
        userImageDto.setImage(ImageUtils.decompressImage(userImage.getImage()));
        return userImageDto;
    }

    @Override
    public UserImageDto getUserImage(UUID userID) {
        UserImage userImage = this.userImageDao.getUserImage(userID).orElseThrow();
        UserImageDto userImageDto = this.modelMapper.map(userImage,UserImageDto.class);
        userImageDto.setImage(ImageUtils.decompressImage(userImage.getImage()));
        return userImageDto;
    }

    @Override
    @Transactional
    @SneakyThrows
    public UserImageDto uploadImage(CreateUserImageDto createUserImageDto) {
        UserImage userImage = this.userImageDao.getUserImage(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        userImage.setImage(ImageUtils.compressImage(createUserImageDto.getFile().getBytes()));
        userImage.setName(createUserImageDto.getFile().getOriginalFilename());
        userImage.setType(createUserImageDto.getFile().getContentType());
        userImage.setOwner(ImageOwner.USER);
        this.userImageDao.save(userImage);
        return this.modelMapper.map(userImage,UserImageDto.class);
    }
}
