package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.services.interfaces.FollowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class FollowController {

    private final FollowService followService;

    @GetMapping("{userID}/followers")
    public ResponseEntity<PaginationResponse<FollowDto>> getFollowers(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<FollowDto> follows = this.followService.findAllFollowers(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(follows.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),follows.getTotalPages(),follows.getTotalElements()));
    }

    @GetMapping("{userID}/followed")
    public ResponseEntity<PaginationResponse<FollowDto>> getFollowed(@PathVariable("userID") Long userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<FollowDto> follows = this.followService.findAllFollowed(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(follows.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),follows.getTotalPages(),follows.getTotalElements()));
    }

    @GetMapping("/follow")
    public ResponseEntity<FollowDto> findFollow(@RequestParam("followerID") Long followerID,@RequestParam("followedID") Long followedID) {
        return ResponseEntity.ok(this.followService.findFollow(followerID,followedID));
    }

    @PostMapping("{followedID}")
    public ResponseEntity<FollowDto> createFollow(@PositiveOrZero @PathVariable("followedID") Long followedID) {
        return ResponseEntity.ok(this.followService.createFollow(followedID));
    }

    @DeleteMapping("{followID}")
    public ResponseEntity<Void> deleteFollow(@PathVariable("followID") Long followID) {
        this.followService.deleteFollows(followID);
        return ResponseEntity.noContent().build();
    }
}
