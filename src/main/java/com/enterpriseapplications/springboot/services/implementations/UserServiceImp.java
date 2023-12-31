package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.SpecificationsUtils;
import com.enterpriseapplications.springboot.data.dao.specifications.UserSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserServiceImp extends GenericServiceImp<User,UserDetailsDto> implements UserService {

    private final UserDao userDao;


    public UserServiceImp(UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<User> pagedResourcesAssembler) {
        super(modelMapper,User.class,UserDetailsDto.class,pagedResourcesAssembler);
        this.userDao = userDao;
    }

    @Override
    public UserDetailsDto getUserDetails(UUID userID) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        UserDetailsDto userDetails = this.modelMapper.map(requiredUser,UserDetailsDto.class);
        userDetails.addLinks();
        return userDetails;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SEARCH_USERS,allEntries = true)
    public UserDetailsDto updateUser(UpdateUserDto updateUserDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(updateUserDto.getName() != null)
            requiredUser.setName(updateUserDto.getName());
        if(updateUserDto.getSurname() != null)
            requiredUser.setSurname(updateUserDto.getSurname());
        if(updateUserDto.getDescription() != null)
            requiredUser.setDescription(updateUserDto.getDescription());
        if(updateUserDto.getGender() != null)
            requiredUser.setGender(updateUserDto.getGender());
        if(updateUserDto.getVisibility() != null)
            requiredUser.setVisibility(updateUserDto.getVisibility());
        requiredUser = this.userDao.save(requiredUser);
        UserDetailsDto userDetailsDto = this.modelMapper.map(requiredUser,UserDetailsDto.class);
        userDetailsDto.addLinks();
        return userDetailsDto;
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_ALL_USERS)
    public PagedModel<UserDetailsDto> getUsers(Pageable pageable) {
        Page<User> users = this.userDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public PagedModel<UserDetailsDto> getUsersBySpec(Specification<User> specification, Pageable pageable) {
        Page<User> users = userDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }


    @Override
    public PagedModel<UserDetailsDto> getSimilarUsers(UUID userID, Pageable pageable) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        UserSpecifications.Filter filter = new UserSpecifications.Filter(SpecificationsUtils.OrderMode.DESCENDED,requiredUser);
        Page<User> users = this.userDao.findAll(UserSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(users,modelAssembler);
    }

    @Override
    public Gender[] getGenders() {
        return Gender.values();
    }

    @Override
    public UserVisibility[] getVisibilities() {
        return UserVisibility.values();
    }

    @Override
    public UserSpecifications.OrderType[] getOrderTypes() {
        return UserSpecifications.OrderType.values();
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConfig.CACHE_ALL_USERS,CacheConfig.CACHE_SEARCH_USERS},allEntries = true)
    public void deleteUser(UUID userID) {
        this.userDao.findById(userID).orElseThrow();
        this.userDao.deleteById(userID);
    }
}
