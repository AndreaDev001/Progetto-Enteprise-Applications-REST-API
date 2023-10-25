package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface OrderService
{
    OrderDto getOrder(UUID orderID);
    PagedModel<OrderDto> getOrders(Pageable pageable);
    PagedModel<OrderDto> getOrders(UUID userID, Pageable pageable);
    OrderDto getOrderByProduct(UUID productID);
    OrderDto createOrder(CreateOrderDto createOrderDto);
    OrderDto updateOrder(UpdateOrderDto updateOrderDto);
    void deleteOrder(UUID orderID);
}
