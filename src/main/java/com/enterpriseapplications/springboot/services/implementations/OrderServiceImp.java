package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.OrderDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
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
    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(createOrderDto.getProductID()).orElseThrow();
        if(requiredUser.getId().equals(requiredProduct.getSeller().getId()))
            throw new InvalidFormat("errors.order.invalidOwner");
        Order order = new Order();
        order.setBuyer(requiredUser);
        order.setProduct(requiredProduct);
        order.setPrice(createOrderDto.getPrice());
        this.orderDao.save(order);
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    public void deleteOrder(Long orderID) {
        this.orderDao.findById(orderID).orElseThrow();
        this.orderDao.deleteById(orderID);
    }
}
