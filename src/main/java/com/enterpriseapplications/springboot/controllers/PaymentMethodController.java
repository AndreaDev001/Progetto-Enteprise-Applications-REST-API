package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/paymentMethods")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PaymentMethodController
{
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethods(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethods(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethods(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethods(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/country/{country}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByCountry(@PathVariable("userID") UUID userID,@PathVariable("country") String country,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByCountry(userID,country,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/brand/{brand}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByBrand(@PathVariable("userID") UUID userID, @PathVariable("brand") String brand, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByBrand(userID,brand,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/name/{name}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByName(@PathVariable("userID") UUID userID,@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByHolderName(userID,name,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @DeleteMapping("{paymentMethodID}")
    public ResponseEntity<Void> deletePaymentMethod(UUID paymentMethodID) {
        this.paymentMethodService.deletePaymentMethod(paymentMethodID);
        return ResponseEntity.noContent().build();
    }
}
