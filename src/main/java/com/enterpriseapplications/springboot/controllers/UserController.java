package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserController
{
    private final UserService userService;

    @GetMapping("{userID}/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable("userID") Long userID) {
        return ResponseEntity.ok(this.userService.getUserDetails(userID));

    }
    @DeleteMapping("{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") Long userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
