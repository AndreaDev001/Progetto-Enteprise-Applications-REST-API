package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferBuyerDto;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.update.offers.UpdateOfferSellerDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OfferController
{
    private final OfferService offerService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OfferDto>> getOffers(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffers(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/private/offer/{userID}/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<OfferDto> getOffer(@PathVariable("userID") UUID userID,@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.offerService.getOffer(userID,productID));
    }

    @GetMapping("private/{offerID}")
    @PreAuthorize("@permissionHandler.hasAccess(@offerDao,#offerID)")
    public ResponseEntity<OfferDto> getOffer(@PathVariable("offerID") UUID offerID) {
        return ResponseEntity.ok(this.offerService.getOffer(offerID));
    }

    @GetMapping("/private/orderTypes")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<OfferSpecifications.OrderType[]> getOrderTypes()
    {
        return ResponseEntity.ok(this.offerService.getOrderTypes());
    }

    @GetMapping("/public/statuses")
    public ResponseEntity<OfferStatus[]> getStatuses() {
        return ResponseEntity.ok(this.offerService.getStatuses());
    }

    @GetMapping("/private/status/{status}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByStatus(@PathVariable("status") OfferStatus status, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByStatus(status, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/private/expired/{expired}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByExpired(@PathVariable("expired") boolean expired, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByExpired(expired,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/private/{userID}/created")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<OfferDto>> getCreatedOffers(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getCreatedOffers(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/private/{userID}/received")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<OfferDto>> getReceivedOffers(@PathVariable("userID") UUID userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getReceivedOffers(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/private/product/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByProductID(@PathVariable("productID") UUID productID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByProductID(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid CreateOfferDto createOfferDto) {
        return ResponseEntity.ok(this.offerService.createOffer(createOfferDto));
    }

    @GetMapping("/private/spec")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OfferDto>> getOffersBySpec(@ParameterObject @Valid OfferSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersBySpec(OfferSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/private/{offerID}/similar")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OfferDto>> getSimilarOffers(@PathVariable("offerID") UUID offerID, Pageable pageable) {
        PagedModel<OfferDto> offers = this.offerService.getSimilarOffers(offerID,pageable);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("private/product/{productID}/status/{status}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByProductIDAndStatus(@PathVariable("productID") UUID productID,@PathVariable("status") OfferStatus status,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByProductIDAndStatus(productID,status,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<OfferDto> updateOffer(@RequestBody @Valid UpdateOfferDto updateOfferDto) {
        return ResponseEntity.ok(this.offerService.updateOffer(updateOfferDto));
    }

    @PutMapping("/private/seller")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#updateOfferSellerDto.productID)")
    public ResponseEntity<OfferDto> updateOfferSeller(@RequestBody @Valid UpdateOfferSellerDto updateOfferSellerDto) {
        return ResponseEntity.ok(this.offerService.updateOfferSeller(updateOfferSellerDto));
    }

    @PutMapping("/private/buyer")
    @PreAuthorize("@permissionHandler.hasAccess(@offerDao,#updateOfferBuyerDto.offerID)")
    public ResponseEntity<OfferDto> updateOfferBuyer(@RequestBody @Valid UpdateOfferBuyerDto updateOfferBuyerDto) {
        return ResponseEntity.ok(this.offerService.updateOfferBuyer(updateOfferBuyerDto));
    }

    @DeleteMapping("private/{offerID}")
    @PreAuthorize("@permissionHandler.hasAccess(@offerDao,#offerID)")
    public ResponseEntity<Void> deleteOffer(@PathVariable("offerID") UUID offerID) {
        this.offerService.deleteOffer(offerID);
        return ResponseEntity.noContent().build();
    }
}
