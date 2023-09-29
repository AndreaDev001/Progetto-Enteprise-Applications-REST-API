package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<User,UserDetailsDto> modelAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;


    public UserServiceImp(UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<User> pagedResourcesAssembler) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(User.class, UserDetailsDto.class,modelMapper);
    }

    @Override
    public UserDetailsDto getUserDetails(Long userID) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        UserDetailsDto userDetails = this.modelMapper.map(requiredUser,UserDetailsDto.class);
        userDetails.setAmountOfFollowers(requiredUser.getFollowers().size());
        userDetails.setAmountOfFollowed(requiredUser.getFollows().size());
        userDetails.setAmountOfProducts(requiredUser.getProducts().size());
        userDetails.setAmountOfOrders(requiredUser.getOrders().size());
        userDetails.addLinks();
        return userDetails;
    }

    @Override
    @Transactional
    public UserDetailsDto updateUser(UpdateUserDto updateUserDto) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(updateUserDto.getName() != null)
            requiredUser.setName(updateUserDto.getName());
        if(updateUserDto.getSurname() != null)
            requiredUser.setSurname(updateUserDto.getSurname());
        if(updateUserDto.getDescription() != null)
            requiredUser.setDescription(updateUserDto.getDescription());
        if(updateUserDto.getGender() != null)
            requiredUser.setGender(updateUserDto.getGender());
        this.userDao.save(requiredUser);
        UserDetailsDto userDetailsDto = this.modelMapper.map(requiredUser,UserDetailsDto.class);
        userDetailsDto.addLinks();
        return userDetailsDto;
    }

    @Override
    public PagedModel<UserDetailsDto> getUsers(Pageable pageable) {
        Page<User> users = this.userDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDetailsDto> getUsersBySpec(Specification<User> specification, Pageable pageable) {
        Page<User> users = this.userDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public UserVisibility[] getVisibilities() {
        return UserVisibility.values();
    }

    @Override
    @Transactional
    public void deleteUser(Long userID) {
        this.userDao.findById(userID).orElseThrow();
        this.userDao.deleteById(userID);
    }
}
