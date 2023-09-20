package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.handlers.CustomerHandler;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;

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
    @Transactional
    public void deleteUser(Long userID) {
        this.userDao.findById(userID).orElseThrow();
        this.userDao.deleteById(userID);
    }
}
