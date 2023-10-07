package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dao.specifications.UserSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService
{
    UserDetailsDto getUserDetails(UUID userID);
    UserDetailsDto updateUser(UpdateUserDto updateUserDto);
    PagedModel<UserDetailsDto> getUsers(Pageable pageable);
    PagedModel<UserDetailsDto> getUsersBySpec(Specification<User> specification, Pageable pageable);
    Gender[] getGenders();
    UserVisibility[] getVisibilities();
    UserSpecifications.OrderType[] getOrderTypes();
    void deleteUser(UUID userID);
}
