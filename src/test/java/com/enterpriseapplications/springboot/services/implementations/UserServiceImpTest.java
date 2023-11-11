package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dao.specifications.UserSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import com.enterpriseapplications.springboot.services.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


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
        this.defaultBefore();
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
        TestUtils.generateValues(authenticatedUser);
        UpdateUserDto updateUserDto = UpdateUserDto.builder().description("description").gender(Gender.MALE).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.userDao.save(any(User.class))).willReturn(firstElement);
        UserDetailsDto result = this.userServiceImp.updateUser(updateUserDto);
        Assert.assertTrue(valid(firstElement,result));

    }

    @Test
    void getUsers() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.userDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<UserDetailsDto> pagedModel = this.userServiceImp.getUsers(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void getUsersBySpec() {
        PageRequest pageRequest = PageRequest.of(0,20);
        UserSpecifications.Filter filter = new UserSpecifications.Filter();
        Specification<User> specification = UserSpecifications.withFilter(filter);
        given(this.userDao.findAll(specification,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<UserDetailsDto> pagedModel = this.userServiceImp.getUsersBySpec(specification,pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }
}