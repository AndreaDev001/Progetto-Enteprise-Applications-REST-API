package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserImageServiceImpTest extends GenericTestImp<UserImage,UserImageDto> {

    private UserImageServiceImp userImageServiceImp;
    @Mock
    private UserDao userDao;
    @Mock
    private UserImageDao userImageDao;

    @Override
    protected void init() {
        super.init();
        this.userImageServiceImp = new UserImageServiceImp(userImageDao,modelMapper,pagedResourcesAssembler, userDao);
        List<Image> images = ImageServiceImpTest.createImages();
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        this.firstElement = new UserImage(images.get(0));
        this.secondElement = new UserImage(images.get(1));
        this.firstElement.setUser(firstUser);
        this.secondElement.setUser(secondUser);
        this.elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }

    public boolean valid(UserImage userImage, UserImageDto userImageDto) {
        ImageServiceImpTest.baseValid(userImage,userImageDto);
        Assert.assertEquals(userImage.getUser().getId(),userImageDto.getUser().getId());
        return true;
    }

    @Test
    void getImage() {
        given(this.userImageDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        UserImageDto userImageDto = this.userImageServiceImp.getImage(firstElement.getId());
        Assert.assertTrue(valid(firstElement,userImageDto));
        given(this.userImageDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        userImageDto = this.userImageServiceImp.getImage(secondElement.getId());
        Assert.assertTrue(valid(secondElement,userImageDto));
    }

    @Test
    void getImages() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.userImageDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<UserImageDto> pagedModel = this.userImageServiceImp.getImages(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void getUserImageDetails() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.userDao.findById(user.getId())).willReturn(Optional.of(user));
        given(this.userImageDao.getUserImage(user.getId())).willReturn(Optional.of(firstElement));
        UserImageDto userImageDto = this.userImageServiceImp.getUserImage(user.getId());
        Assert.assertTrue(valid(firstElement,userImageDto));
    }

    @Test
    void getUserImage() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.userDao.findById(user.getId())).willReturn(Optional.of(user));
        given(this.userImageDao.getUserImage(user.getId())).willReturn(Optional.of(firstElement));
        UserImageDto userImageDto = this.userImageServiceImp.getUserImage(user.getId());
        Assert.assertTrue(valid(firstElement,userImageDto));
    }
}