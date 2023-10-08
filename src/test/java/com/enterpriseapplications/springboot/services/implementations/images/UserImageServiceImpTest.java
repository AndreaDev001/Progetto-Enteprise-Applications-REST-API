package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.services.interfaces.images.UserImageService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserImageServiceImpTest {

    private UserImageServiceImp userImageServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<UserImage> pagedResourcesAssembler;
    private UserImage firstImage;
    private UserImage secondImage;

    @Mock
    private UserImageDao userImageDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        this.userImageServiceImp = new UserImageServiceImp(userImageDao,modelMapper,pagedResourcesAssembler);
        List<Image> images = ImageServiceImpTest.createImages();
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        this.firstImage = new UserImage(images.get(0));
        this.secondImage = new UserImage(images.get(1));
        this.firstImage.setUser(firstUser);
        this.secondImage.setUser(secondUser);
    }

    boolean valid(UserImage userImage, UserImageDto userImageDto) {
        ImageServiceImpTest.valid(userImage,userImageDto);
        Assert.assertEquals(userImage.getUser().getId(),userImageDto.getUser().getId());
        return true;
    }

    @Test
    void getImage() {
        given(this.userImageDao.findById(firstImage.getId())).willReturn(Optional.of(firstImage));
        given(this.userImageDao.findById(secondImage.getId())).willReturn(Optional.of(secondImage));
        UserImageDto firstImage = this.userImageServiceImp.getImage(this.firstImage.getId());
        UserImageDto secondImage = this.userImageServiceImp.getImage(this.secondImage.getId());
        Assert.assertTrue(valid(this.firstImage,firstImage));
        Assert.assertTrue(valid(this.secondImage,secondImage));
    }

    @Test
    void getImages() {
    }

    @Test
    void getUserImageDetails() {
    }

    @Test
    void getUserImage() {
    }

    @Test
    void uploadImage() {
    }
}