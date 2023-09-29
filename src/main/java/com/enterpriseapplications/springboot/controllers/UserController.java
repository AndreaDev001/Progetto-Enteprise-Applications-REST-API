package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dao.specifications.UserSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserController
{
    private final UserService userService;

    @GetMapping("{userID}/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable("userID") Long userID) {
        return ResponseEntity.ok(this.userService.getUserDetails(userID));

    }

    @GetMapping
    public ResponseEntity<PagedModel<UserDetailsDto>> getUsers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDetailsDto> users = this.userService.getUsers(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/spec")
    public ResponseEntity<PagedModel<UserDetailsDto>> getUsersBySpec(@ParameterObject @Valid UserSpecifications.Filter filter, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserDetailsDto> users = this.userService.getUsersBySpec(UserSpecifications.withFilter(filter), PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(this.userService.updateUser(updateUserDto));

    }
    @GetMapping("/visibilities")
    public ResponseEntity<UserVisibility[]> getVisibilities() {
        return ResponseEntity.ok(this.userService.getVisibilities());
    }
    @DeleteMapping("{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") Long userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
