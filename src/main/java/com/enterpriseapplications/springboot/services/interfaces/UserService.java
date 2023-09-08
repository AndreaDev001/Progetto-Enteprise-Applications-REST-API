package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;

public interface UserService
{
    UserDetailsDto getUserDetails(Long userID);
}
