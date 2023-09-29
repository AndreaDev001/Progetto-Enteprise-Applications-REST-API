package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import com.enterpriseapplications.springboot.services.interfaces.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OrderController
{
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PagedModel<OrderDto>> getOrders(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OrderDto> orders = this.orderService.getOrders(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/buyer/{userID}")
    public ResponseEntity<PagedModel<OrderDto>> getOrders(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OrderDto> orders = this.orderService.getOrders(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/product/{productID}")
    public ResponseEntity<OrderDto> findOrder(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.orderService.getOrder(productID));
    }

    @DeleteMapping("{orderID}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("orderID") UUID orderID) {
        this.orderService.deleteOrder(orderID);
        return ResponseEntity.noContent().build();
    }
}
