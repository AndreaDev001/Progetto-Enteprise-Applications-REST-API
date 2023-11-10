package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.enums.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.UUID;

public interface OrderService
{
    OrderDto getOrder(UUID orderID);
    PagedModel<OrderDto> getOrders(Pageable pageable);
    PagedModel<OrderDto> getOrders(UUID userID, Pageable pageable);
    List<OrderDto> getOrders(OrderStatus status);
    OrderDto getOrderByProduct(UUID productID);
    OrderDto createOrder(CreateOrderDto createOrderDto);
    OrderStatus[] getStatues();
    void updateProcessing();
    void updateShipping();
    void deleteOrder(UUID orderID);
}
