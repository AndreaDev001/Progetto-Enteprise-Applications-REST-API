package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.PermissionHandler;
import com.enterpriseapplications.springboot.data.dao.specifications.UserSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserController
{
    private final UserService userService;

    @GetMapping("/public/{userID}/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.userService.getUserDetails(userID));

    }

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<UserDetailsDto>> getUsers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDetailsDto> users = this.userService.getUsers(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(users);
    }


    @GetMapping("/public/spec")
    @Cacheable(value = CacheConfig.CACHE_SEARCH_USERS,key = "{#filter.toString(),#paginationRequest.toString()}")
    public ResponseEntity<PagedModel<UserDetailsDto>> getUsersBySpec(@ParameterObject @Valid UserSpecifications.Filter filter, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDetailsDto> users = this.userService.getUsersBySpec(UserSpecifications.withFilter(filter), PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/public/{userID}/similar")
    public ResponseEntity<PagedModel<UserDetailsDto>> getSimilarUsers(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDetailsDto> users = this.userService.getSimilarUsers(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(users);
    }

    @PutMapping("/public")
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(this.userService.updateUser(updateUserDto));
    }
    @GetMapping("/public/visibilities")
    public ResponseEntity<UserVisibility[]> getVisibilities() {
        return ResponseEntity.ok(this.userService.getVisibilities());
    }
    @GetMapping("/public/genders")
    public ResponseEntity<Gender[]> getGenders() {
        return ResponseEntity.ok(this.userService.getGenders());
    }
    @GetMapping("/public/orderTypes")
    public ResponseEntity<UserSpecifications.OrderType[]> getOrderTypes() {
        return ResponseEntity.ok(this.userService.getOrderTypes());
    }

    @DeleteMapping("/private/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") UUID userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
