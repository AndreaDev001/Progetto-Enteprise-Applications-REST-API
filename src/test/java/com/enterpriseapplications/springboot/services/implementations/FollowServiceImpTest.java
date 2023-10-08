package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.FollowDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import com.enterpriseapplications.springboot.data.entities.User;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class FollowServiceImpTest {

    private FollowServiceImp followServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Follow> pagedResourcesAssembler;
    private Follow firstFollow;
    private Follow secondFollow;

    @Mock
    private FollowDao followDao;
    @Mock
    private UserDao userDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        followServiceImp = new FollowServiceImp(followDao,userDao,modelMapper,pagedResourcesAssembler);
        User follower = User.builder().id(UUID.randomUUID()).build();
        User followed = User.builder().id(UUID.randomUUID()).build();
        firstFollow = Follow.builder().id(UUID.randomUUID()).follower(follower).followed(followed).build();
        secondFollow = Follow.builder().id(UUID.randomUUID()).follower(followed).followed(follower).build();
    }
    boolean valid(Follow follow, FollowDto followDto) {
        Assert.assertNotNull(followDto);
        Assert.assertEquals(follow.getId(),followDto.getId());
        Assert.assertEquals(follow.getFollowed().getId(),followDto.getFollowed().getId());
        Assert.assertEquals(follow.getFollower().getId(),followDto.getFollower().getId());
        Assert.assertEquals(follow.getCreatedDate(),followDto.getCreatedDate());
        return true;
    }
    @Test
    void getFollow() {
        given(this.followDao.findById(firstFollow.getId())).willReturn(Optional.of(firstFollow));
        given(this.followDao.findById(secondFollow.getId())).willReturn(Optional.of(secondFollow));
        FollowDto firstFollow = this.followServiceImp.getFollow(this.firstFollow.getId());
        FollowDto secondFollow = this.followServiceImp.getFollow(this.secondFollow.getId());
        Assert.assertTrue(valid(this.firstFollow,firstFollow));
        Assert.assertTrue(valid(this.secondFollow,secondFollow));
    }

    @Test
    void getFollows() {
    }

    @Test
    void findAllFollowers() {
    }

    @Test
    void findAllFollowed() {
    }

    @Test
    void findFollow() {
    }

    @Test
    void createFollow() {
    }
}