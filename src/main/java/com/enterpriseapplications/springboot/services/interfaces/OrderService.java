package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService
{
    Page<OrderDto> getOrders(Long userID, Pageable pageable);
    OrderDto getOrder(Long productID);
    OrderDto createOrder(CreateOrderDto createOrderDto);
    void deleteOrder(Long orderID);
}
