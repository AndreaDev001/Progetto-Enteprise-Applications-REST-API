package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.OrderDao;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import com.enterpriseapplications.springboot.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<OrderDto> getOrders(Long userID, Pageable pageable) {
        Page<Order> orders = this.orderDao.getOrders(userID,pageable);
        return new PageImpl<>(orders.stream().map(order -> this.modelMapper.map(order,OrderDto.class)).collect(Collectors.toList()),pageable,orders.getTotalElements());
    }

    @Override
    public OrderDto getOrder(Long productID) {
        Order order = this.orderDao.findOrder(productID).orElseThrow();
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long productID) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderID) {
        this.orderDao.findById(orderID).orElseThrow();
        this.orderDao.deleteById(orderID);
    }
}
