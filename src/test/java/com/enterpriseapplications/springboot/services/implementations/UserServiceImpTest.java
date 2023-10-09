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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImpTest extends GenericTestImp<User,UserDetailsDto> {

    private UserServiceImp userServiceImp;
    @Mock
    private UserDao userDao;


    @Override
    protected void init() {
        super.init();
        userServiceImp = new UserServiceImp(this.userDao,modelMapper,pagedResourcesAssembler);
        firstElement = User.builder().id(UUID.randomUUID()).email("marchioandrea02@gmail.com").username("andrea").name("andrea").surname("marchio").gender(Gender.MALE).build();
        secondElement = User.builder().id(UUID.randomUUID()).email("andreamarchio01@gmail.com").username("andrea").name("andrea").surname("marchio").gender(Gender.MALE).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }


   public boolean valid(User user, UserDetailsDto userDetails) {
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
        given(this.userDao.findById(this.firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.userDao.findById(this.secondElement.getId())).willReturn(Optional.of(secondElement));
        UserDetailsDto firstDetails = this.userServiceImp.getUserDetails(firstElement.getId());
        UserDetailsDto secondDetails = this.userServiceImp.getUserDetails(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstDetails));
        Assert.assertTrue(valid(secondElement,secondDetails));
    }

    @Test
    void updateUser() {

    }

    @Test
    void getUsers() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.userDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<UserDetailsDto> pagedModel = this.userServiceImp.getUsers(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
    }

    @Test
    void getUsersBySpec() {
    }
}