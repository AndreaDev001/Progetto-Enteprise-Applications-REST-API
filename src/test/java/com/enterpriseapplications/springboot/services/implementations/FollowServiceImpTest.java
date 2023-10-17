package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.FollowDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import com.enterpriseapplications.springboot.data.entities.User;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class FollowServiceImpTest extends GenericTestImp<Follow,FollowDto> {

    private FollowServiceImp followServiceImp;
    @Mock
    private FollowDao followDao;
    @Mock
    private UserDao userDao;

    @Override
    protected void init() {
        super.init();
        followServiceImp = new FollowServiceImp(followDao,userDao,modelMapper,pagedResourcesAssembler);
        User follower = User.builder().id(UUID.randomUUID()).build();
        User followed = User.builder().id(UUID.randomUUID()).build();
        firstElement = Follow.builder().id(UUID.randomUUID()).follower(follower).followed(followed).build();
        secondElement = Follow.builder().id(UUID.randomUUID()).follower(followed).followed(follower).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    @Test
    void getFollow() {
        given(this.followDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.followDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        FollowDto firstFollow = this.followServiceImp.getFollow(this.firstElement.getId());
        FollowDto secondFollow = this.followServiceImp.getFollow(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstFollow));
        Assert.assertTrue(valid(this.secondElement,secondFollow));
    }

    @Test
    void getFollows() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.followDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<FollowDto> pagedModel = this.followServiceImp.getFollows(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void findAllFollowers() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.followDao.findAllFollowers(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<FollowDto> pagedModel = this.followServiceImp.findAllFollowers(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void findAllFollowed() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.followDao.findAllFollows(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<FollowDto> pagedModel = this.followServiceImp.findAllFollowed(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void findFollow() {
        User follower = User.builder().id(UUID.randomUUID()).build();
        User followed = User.builder().id(UUID.randomUUID()).build();
        given(this.followDao.findFollow(follower.getId(),followed.getId())).willReturn(Optional.of(firstElement));
        Assert.assertTrue(valid(firstElement,this.followServiceImp.findFollow(follower.getId(),followed.getId())));
    }

    @Test
    void createFollow() {
        User followedUser = User.builder().id(UUID.randomUUID()).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.userDao.findById(followedUser.getId())).willReturn(Optional.of(followedUser));
        when(this.followDao.save(any(Follow.class))).thenReturn(firstElement);
        FollowDto followDto = this.followServiceImp.createFollow(followedUser.getId());
        Assert.assertTrue(valid(firstElement,followDto));
    }

    @Override
    public boolean valid(Follow follow, FollowDto followDto) {
        Assert.assertNotNull(followDto);
        Assert.assertEquals(follow.getId(),followDto.getId());
        Assert.assertEquals(follow.getFollowed().getId(),followDto.getFollowed().getId());
        Assert.assertEquals(follow.getFollower().getId(),followDto.getFollower().getId());
        Assert.assertEquals(follow.getCreatedDate(),followDto.getCreatedDate());
        return true;
    }
}