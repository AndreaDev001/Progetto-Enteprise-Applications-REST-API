package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.output.user.UserDetailsDto;
import com.enterpriseapplications.springboot.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("{id}/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.getUserDetails(id));

    }
    @DeleteMapping("{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") Long userID) {
        this.userService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }
}
