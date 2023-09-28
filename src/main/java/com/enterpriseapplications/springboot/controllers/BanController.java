package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateBanDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateBanDto;
import com.enterpriseapplications.springboot.data.dto.output.BanDto;
import com.enterpriseapplications.springboot.services.interfaces.BanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bans")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class BanController
{
    private final BanService banService;


    @GetMapping("/banner/{userID}")
    public ResponseEntity<PagedModel<BanDto>> getCreatedBans(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getCreatedBans(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/banned/{userID}")
    public ResponseEntity<PagedModel<BanDto>> getReceivedBans(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<BanDto> bans = this.banService.getReceivedBans(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(bans);
    }

    @GetMapping("/banned/{userID}/active")
    public ResponseEntity<BanDto> findBan(@PathVariable("userID") Long userID) {
        return ResponseEntity.ok(this.banService.getCurrentBan(userID));
    }

    @PostMapping
    public ResponseEntity<BanDto> createBan(@RequestBody @Valid CreateBanDto createBanDto) {
        return ResponseEntity.ok(this.banService.createBan(createBanDto));
    }

    @PutMapping
    public ResponseEntity<BanDto> updateBan(@RequestBody @Valid UpdateBanDto updateBanDto) {
        return ResponseEntity.ok(this.banService.updateBan(updateBanDto));
    }

    @DeleteMapping("{banID}")
    public ResponseEntity<BanDto> deleteBan(@PathVariable("banID") Long banID) {
        this.banService.deleteBan(banID);
        return ResponseEntity.noContent().build();
    }
}
