package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("offers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OfferController
{
    private final OfferService offerService;

    @GetMapping("/status/{status}")
    public ResponseEntity<PaginationResponse<OfferDto>> getOffersByStatus(@PathVariable("status") OfferStatus status, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OfferDto> offers = this.offerService.getOffersByStatus(status, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(offers.toList(),paginationRequest.getPage(), paginationRequest.getPageSize(),offers.getTotalPages(),offers.getTotalElements()));
    }
    @GetMapping("/expired/{expired}")
    public ResponseEntity<PaginationResponse<OfferDto>> getOffersByExpired(@PathVariable("expired") boolean expired, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OfferDto> offers = this.offerService.getOffersByExpired(expired,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(offers.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),offers.getTotalPages(),offers.getTotalElements()));
    }
    @GetMapping("/buyer/{userID}")
    public ResponseEntity<PaginationResponse<OfferDto>> getOffersByBuyerID(@PathVariable("userID") Long userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OfferDto> offers = this.offerService.getOffersByBuyerID(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(offers.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),offers.getTotalPages(),offers.getTotalElements()));
    }
    @GetMapping("/product/{productID}")
    public ResponseEntity<PaginationResponse<OfferDto>> getOffersByProductID(@PathVariable("productID") Long productID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OfferDto> offers = this.offerService.getOffersByProductID(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(offers.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),offers.getTotalPages(),offers.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid CreateOfferDto createOfferDto) {
        return ResponseEntity.ok(this.offerService.createOffer(createOfferDto));
    }

    @GetMapping("/product/{productID}/status/{status}")
    public ResponseEntity<PaginationResponse<OfferDto>> getOffersByProductIDAndStatus(@PathVariable("productID") Long productID,@PathVariable("status") OfferStatus status,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OfferDto> offers = this.offerService.getOffersByProductIDAndStatus(productID,status,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(offers.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),offers.getTotalPages(),offers.getTotalElements()));
    }
    @DeleteMapping("{offerID}")
    public ResponseEntity<Void> deleteOffer(@PathVariable("offerID") Long offerID) {
        this.offerService.deleteOffer(offerID);
        return ResponseEntity.noContent().build();
    }
}
