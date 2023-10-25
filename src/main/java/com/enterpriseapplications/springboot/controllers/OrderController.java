package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.input.create.CreatePaymentMethodDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateOrderDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class OrderController
{
    private final OrderService orderService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<OrderDto>> getOrders(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OrderDto> orders = this.orderService.getOrders(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/private/{orderID}")
    @PreAuthorize("@permissionHandler.hasAccess(@orderDao,#orderID)")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderID") UUID orderID) {
        return ResponseEntity.ok(this.orderService.getOrder(orderID));
    }

    @GetMapping("/public/buyer/{userID}")
    @PreAuthorize("@permissionHandler.hasAccess(#userID)")
    public ResponseEntity<PagedModel<OrderDto>> getOrders(@PathVariable("userID") UUID userID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<OrderDto> orders = this.orderService.getOrders(userID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/private")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(this.orderService.createOrder(createOrderDto));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid UpdateOrderDto updateOrderDto) {
        return ResponseEntity.ok(this.orderService.updateOrder(updateOrderDto));
    }

    @GetMapping("/private/product/{productID}")
    @PreAuthorize("@permissionHandler.hasAccess(@productDao,#productID)")
    public ResponseEntity<OrderDto> findOrder(@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.orderService.getOrder(productID));
    }

    @DeleteMapping("/private/{orderID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("orderID") UUID orderID) {
        this.orderService.deleteOrder(orderID);
        return ResponseEntity.noContent().build();
    }
}
