package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import com.enterpriseapplications.springboot.data.entities.enums.PaymentMethodBrand;
import com.enterpriseapplications.springboot.services.interfaces.PaymentMethodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/paymentMethods")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PaymentMethodController
{
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethods(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethods(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/private/{paymentMethodID}")
    @PreAuthorize("@permissionHandler.hasAccess(@paymentMethodDao,#paymentMethodID)")
    public ResponseEntity<PaymentMethodDto> getPaymentMethod(@PathVariable("paymentMethodID") UUID paymentMethodID) {
        return ResponseEntity.ok(this.paymentMethodService.getPaymentMethod(paymentMethodID));
    }

    @GetMapping("/private/owner/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods(@PathVariable("userID") UUID userID) {
        List<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethods(userID);
        return ResponseEntity.ok(paymentMethods);
    }
    @GetMapping("/private/owner/{userID}/brand/{brand}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByBrand(@PathVariable("userID") UUID userID, @PathVariable("brand") String brand, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByBrand(userID,brand,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<PaymentMethodDto> createPaymentMethod(@RequestBody @Valid CreatePaymentMethodDto createPaymentMethodDto) {
        return ResponseEntity.ok(this.paymentMethodService.createPaymentMethod(createPaymentMethodDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@paymentMethodDao,#updatePaymentMethodDto.paymentMethodID")
    public ResponseEntity<PaymentMethodDto> updatePaymentMethod(@RequestBody @Valid UpdatePaymentMethodDto updatePaymentMethodDto) {
        return ResponseEntity.ok(this.paymentMethodService.updatePaymentMethod(updatePaymentMethodDto));
}

    @GetMapping("/private/owner/{userID}/name/{name}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByName(@PathVariable("userID") UUID userID,@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByHolderName(userID,name,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @DeleteMapping("/private/{paymentMethodID}")
    @PreAuthorize("@permissionHandler.hasAccess(@paymentMethodDao,#paymentMethodID)")
    public ResponseEntity<Void> deletePaymentMethod(UUID paymentMethodID) {
        this.paymentMethodService.deletePaymentMethod(paymentMethodID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/brands")
    public ResponseEntity<PaymentMethodBrand[]> getBrands() {
        return ResponseEntity.ok(this.paymentMethodService.getBrands());
    }
}
