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

@RestController
@RequestMapping("/paymentMethods")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class PaymentMethodController
{
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/owner/{userID}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethods(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethods(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/country/{country}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByCountry(@PathVariable("userID") Long userID,@PathVariable("country") String country,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByCountry(userID,country,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/brand/{brand}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByBrand(@PathVariable("userID") Long userID, @PathVariable("brand") String brand, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByBrand(userID,brand,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/owner/{userID}/name/{name}")
    public ResponseEntity<PagedModel<PaymentMethodDto>> getPaymentMethodsByName(@PathVariable("userID") Long userID,@PathVariable("name") String name,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<PaymentMethodDto> paymentMethods = this.paymentMethodService.getPaymentMethodsByHolderName(userID,name,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(paymentMethods);
    }

    @DeleteMapping("{paymentMethodID}")
    public ResponseEntity<Void> deletePaymentMethod(Long paymentMethodID) {
        this.paymentMethodService.deletePaymentMethod(paymentMethodID);
        return ResponseEntity.noContent().build();
    }
}
