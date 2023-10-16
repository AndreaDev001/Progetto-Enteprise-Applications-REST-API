package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
import com.enterpriseapplications.springboot.services.implementations.UserServiceImp;
import com.enterpriseapplications.springboot.services.interfaces.images.UserImageService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserImageServiceImp extends GenericServiceImp<UserImage,UserImageDto> implements UserImageService
{
    private final UserImageDao userImageDao;

    public UserImageServiceImp(UserImageDao userImageDao,ModelMapper modelMapper,PagedResourcesAssembler<UserImage> pagedResourcesAssembler) {
        super(modelMapper,UserImage.class,UserImageDto.class,pagedResourcesAssembler);
        this.userImageDao = userImageDao;
    }

    @Override
    public UserImageDto getImage(UUID id) {
        return this.modelMapper.map(this.userImageDao.findById(id).orElseThrow(),UserImageDto.class);
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
