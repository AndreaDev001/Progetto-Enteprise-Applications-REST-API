package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;

public interface UserService
{
    UserDetailsDto getUserDetails(Long userID);
    UserDetailsDto updateUser(UpdateUserDto updateUserDto);
    UserVisibility[] getVisibilities();
    void deleteUser(Long userID);
}
