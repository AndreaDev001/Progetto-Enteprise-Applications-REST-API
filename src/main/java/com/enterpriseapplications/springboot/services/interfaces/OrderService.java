package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService
{
    Page<OrderDto> getOrders(Long userID, Pageable pageable);
    OrderDto getOrder(Long productID);
    void deleteOrder(Long orderID);
}
