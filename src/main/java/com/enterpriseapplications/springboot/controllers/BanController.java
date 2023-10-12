package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.data.dao.specifications.BanSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bans")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BanController
{
    private final BanService banService;

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<BanDto>> getBans(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBans(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping("{banID}")
    public ResponseEntity<BanDto> getBan(@PathVariable("banID") UUID banID) {
        return ResponseEntity.ok(this.banService.getBan(banID));
    }

    @GetMapping("/orderTypes")
    public ResponseEntity<BanSpecifications.OrderType[]> getOrderTypes() {
        return ResponseEntity.ok(this.banService.getOrderTypes());
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping("/banner/{userID}")
    public ResponseEntity<PagedModel<BanDto>> getCreatedBans(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getCreatedBans(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping("/banned/{userID}")
    public ResponseEntity<PagedModel<BanDto>> getReceivedBans(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getReceivedBans(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @Cacheable(value = CacheConfig.CACHE_SEARCH_BANS,key = "{#filter.toString(),#paginationRequest.toString()}")
    @GetMapping("/public/spec")
    public ResponseEntity<PagedModel<BanDto>> getBans(@ParameterObject @Valid BanSpecifications.Filter filter, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getBansBySpec(BanSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping("/banned/{userID}/active")
    public ResponseEntity<BanDto> findBan(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.banService.getCurrentBan(userID));
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BanDto> createBan(@RequestBody @Valid CreateBanDto createBanDto) {
        return ResponseEntity.ok(this.banService.createBan(createBanDto));
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<BanDto> updateBan(@RequestBody @Valid UpdateBanDto updateBanDto) {
        return ResponseEntity.ok(this.banService.updateBan(updateBanDto));
    }

    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @DeleteMapping("{banID}")
    public ResponseEntity<BanDto> deleteBan(@PathVariable("banID") UUID banID) {
        this.banService.deleteBan(banID);
        return ResponseEntity.noContent().build();
    }
}
