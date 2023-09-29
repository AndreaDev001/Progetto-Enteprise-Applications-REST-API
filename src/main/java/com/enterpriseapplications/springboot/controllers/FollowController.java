package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.services.interfaces.FollowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class FollowController {

    private final FollowService followService;

    @GetMapping("{userID}/followers")
    public ResponseEntity<PagedModel<FollowDto>> getFollowers(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<FollowDto> pagedModel = this.followService.findAllFollowers(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("{userID}/followed")
    public ResponseEntity<PagedModel<FollowDto>> getFollowed(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<FollowDto> pagedModel = this.followService.findAllFollowed(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/follow")
    public ResponseEntity<FollowDto> findFollow(@RequestParam("followerID") UUID followerID,@RequestParam("followedID") UUID followedID) {
        return ResponseEntity.ok(this.followService.findFollow(followerID,followedID));
    }

    @PostMapping("{followedID}")
    public ResponseEntity<FollowDto> createFollow(@PositiveOrZero @PathVariable("followedID") UUID followedID) {
        return ResponseEntity.ok(this.followService.createFollow(followedID));
    }

    @DeleteMapping("{followID}")
    public ResponseEntity<Void> deleteFollow(@PathVariable("followID") UUID followID) {
        this.followService.deleteFollows(followID);
        return ResponseEntity.noContent().build();
    }
}
