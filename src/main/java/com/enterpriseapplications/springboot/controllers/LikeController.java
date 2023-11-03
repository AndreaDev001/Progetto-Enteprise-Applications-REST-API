package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.LikeDto;
import com.enterpriseapplications.springboot.services.interfaces.LikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<LikeDto>> getLikes(@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikes(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/private/like/{userID}/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<LikeDto> getLike(@PathVariable("userID") UUID userID,@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.likeService.getLike(userID,productID));
    }

    @GetMapping("/private/{likeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@likeDao,#likeID)")
    public ResponseEntity<LikeDto> getLike(@PathVariable("likeID") UUID likeID) {
        return ResponseEntity.ok(this.likeService.getLike(likeID));
    }
    @GetMapping("/private/user/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByUser(@PathVariable("userID") UUID userID, @Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByUser(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(likes);
    }
    @GetMapping("/private/product/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<PagedModel<LikeDto>> getLikesByProduct(@PathVariable("productID") UUID productID,@Valid @ParameterObject PaginationRequest paginationRequest) {
        PagedModel<LikeDto> likes = this.likeService.getLikesByProduct(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(likes);
    }
    @PostMapping("/private/{productID}")
    public ResponseEntity<LikeDto> createLike(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.likeService.createLike(productID));
    }

    @DeleteMapping("/private/{likeID}")
    @PreAuthorize("@permissionHandler.hasAccess(@likeDao,#likeID)")
    public ResponseEntity<Void> deleteLike(@PathVariable("likeID") UUID likeID) {
        this.likeService.deleteLike(likeID);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/product/{productID}")
    public ResponseEntity<Void> deleteLikeByProduct(@PathVariable("productID") UUID productID) {
        this.likeService.deleteLikeByProduct(productID);
        return ResponseEntity.noContent().build();
    }
}
