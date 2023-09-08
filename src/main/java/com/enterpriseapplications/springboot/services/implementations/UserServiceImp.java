package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Override
    public UserDetailsDto getUserDetails(Long userID) {
        User requiredUser = this.userDao.findById(userID).orElseThrow();
        return this.modelMapper.map(requiredUser,UserDetailsDto.class);
    }
}
