package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import com.enterpriseapplications.springboot.services.interfaces.OfferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OfferController
{
    private final OfferService offerService;

    @GetMapping("/status/{status}")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByStatus(@PathVariable("status") OfferStatus status, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByStatus(status, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/expired/{expired}")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByExpired(@PathVariable("expired") boolean expired, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByExpired(expired,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/buyer/{userID}")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByBuyerID(@PathVariable("userID") Long userID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByBuyerID(userID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/product/{productID}")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByProductID(@PathVariable("productID") Long productID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByProductID(productID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid CreateOfferDto createOfferDto) {
        return ResponseEntity.ok(this.offerService.createOffer(createOfferDto));
    }

    @GetMapping("/spec")
    public ResponseEntity<PagedModel<OfferDto>> getOffersBySpec(@ParameterObject @Valid OfferSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersBySpec(OfferSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/product/{productID}/status/{status}")
    public ResponseEntity<PagedModel<OfferDto>> getOffersByProductIDAndStatus(@PathVariable("productID") Long productID,@PathVariable("status") OfferStatus status,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OfferDto> offers = this.offerService.getOffersByProductIDAndStatus(productID,status,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(offers);
    }
    @DeleteMapping("{offerID}")
    public ResponseEntity<Void> deleteOffer(@PathVariable("offerID") Long offerID) {
        this.offerService.deleteOffer(offerID);
        return ResponseEntity.noContent().build();
    }
}
