package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.services.interfaces.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OrderController
{
    private final OrderService orderService;

    @GetMapping("/buyer/{userID}")
    public ResponseEntity<PaginationResponse<OrderDto>> getOrders(@PathVariable("userID") Long userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<OrderDto> orders = this.orderService.getOrders(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(orders.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),orders.getTotalPages(),orders.getTotalElements()));
    }

    @GetMapping("/product/{productID}")
    public ResponseEntity<OrderDto> findOrder(@PathVariable("productID") Long productID) {
        return ResponseEntity.ok(this.orderService.getOrder(productID));
    }

    @DeleteMapping("{orderID}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("orderID") Long orderID) {
        this.orderService.deleteOrder(orderID);
        return ResponseEntity.noContent().build();
    }
}
