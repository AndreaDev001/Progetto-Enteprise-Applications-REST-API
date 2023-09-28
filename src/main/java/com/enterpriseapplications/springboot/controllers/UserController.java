package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
