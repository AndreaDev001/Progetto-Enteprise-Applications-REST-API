package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserImageServiceImp extends GenericServiceImp<UserImage,UserImageDto> implements UserImageService
{
    private final UserImageDao userImageDao;
    private final UserDao userDao;

    public UserImageServiceImp(UserImageDao userImageDao, ModelMapper modelMapper, PagedResourcesAssembler<UserImage> pagedResourcesAssembler, UserDao userDao) {
        super(modelMapper,UserImage.class,UserImageDto.class,pagedResourcesAssembler);
        this.userImageDao = userImageDao;
        this.userDao = userDao;
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
        return userImageDto;
    }

    @Override
    @Transactional
    @SneakyThrows
    public UserImageDto getUserImage(UUID userID) {
            User requiredUser = this.userDao.findById(userID).orElseThrow();
            Optional<UserImage> userImageOptional = this.userImageDao.getUserImage(userID);
            UserImage userImage = null;
            if(userImageOptional.isEmpty()) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultUserImage.jpg");
                userImage = new UserImage();
                userImage.setType(ImageType.IMAGE_JPEG);
                userImage.setImage(inputStream.readAllBytes());
                userImage.setUser(requiredUser);
                this.userImageDao.save(userImage);
            }
            else
                userImage = userImageOptional.get();
            UserImageDto userImageDto = this.modelMapper.map(userImage,UserImageDto.class);
            return userImageDto;
    }

    @Override
    @Transactional
    @SneakyThrows
    public UserImageDto uploadImage(CreateUserImageDto createUserImageDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Optional<UserImage> userImageOptional = this.userImageDao.getUserImage(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        UserImage userImage = userImageOptional.orElseGet(UserImage::new);
        userImage.setImage(createUserImageDto.getFile().getBytes());
        userImage.setType(ImageUtils.getImageType(createUserImageDto.getFile().getContentType()));
        userImage.setUser(requiredUser);
        userImage = this.userImageDao.save(userImage);
        UserImageDto userImageDto = this.modelMapper.map(userImage,UserImageDto.class);
        userImageDto.addLinks();
        return userImageDto;
    }
}
