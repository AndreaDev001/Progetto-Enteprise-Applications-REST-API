package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    private UserServiceImp userServiceImp;
    private User firstUser;
    private User secondUser;
    private PagedResourcesAssembler<User> pagedResourcesAssembler;
    private ModelMapper modelMapper;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void before() {
        firstUser = new User();
        secondUser = new User();
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        userServiceImp = new UserServiceImp(this.userDao,modelMapper,pagedResourcesAssembler);
        firstUser.setId(UUID.randomUUID());
        firstUser.setEmail("marchioandrea02@gmail.com");
        firstUser.setUsername("andrea");
        firstUser.setName("andrea");
        firstUser.setSurname("marchio");
        firstUser.setGender(Gender.MALE);
        secondUser.setId(UUID.randomUUID());
        secondUser.setEmail("andreamarchio01@gmail.com");
        secondUser.setUsername("andrea1");
        secondUser.setName("andrea");
        secondUser.setSurname("marchio");
        secondUser.setGender(Gender.MALE);
    }


    boolean valid(User user, UserDetailsDto userDetails) {
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(user.getId(),userDetails.getId());
        Assert.assertEquals(user.getEmail(),userDetails.getEmail());
        Assert.assertEquals(user.getUsername(),userDetails.getUsername());
        Assert.assertEquals(user.getDescription(),userDetails.getDescription());
        Assert.assertEquals(user.getName(),userDetails.getName());
        Assert.assertEquals(user.getSurname(),userDetails.getSurname());
        Assert.assertEquals(user.getGender(),userDetails.getGender());
        Assert.assertEquals(user.getRating(),userDetails.getRating());
        Assert.assertEquals(user.getCreatedDate(),userDetails.getCreatedDate());
        Assert.assertEquals(user.getVisibility(),userDetails.getVisibility());
        Assert.assertEquals(user.getProducts().size(),userDetails.getAmountOfProducts());
        Assert.assertEquals(user.getReceivedBans().size(),userDetails.getAmountOfReceivedBans());
        Assert.assertEquals(user.getWrittenReviews().size(),userDetails.getAmountOfWrittenReviews());
        Assert.assertEquals(user.getReceivedReviews().size(),userDetails.getAmountOfReceivedReviews());
        Assert.assertEquals(user.getFollowers().size(),userDetails.getAmountOfFollowers());
        Assert.assertEquals(user.getWrittenReplies().size(),userDetails.getAmountOfReplies());
        return true;
    }
    @Test
    void getUserDetails() {
        given(this.userDao.findById(this.firstUser.getId())).willReturn(Optional.of(firstUser));
        given(this.userDao.findById(this.secondUser.getId())).willReturn(Optional.of(secondUser));
        UserDetailsDto firstDetails = this.userServiceImp.getUserDetails(firstUser.getId());
        UserDetailsDto secondDetails = this.userServiceImp.getUserDetails(secondUser.getId());
        Assert.assertTrue(valid(firstUser,firstDetails));
        Assert.assertTrue(valid(secondUser,secondDetails));
    }

    @Test
    void updateUser() {

    }

    @Test
    void getUsers() {
    }

    @Test
    void getUsersBySpec() {
    }
}