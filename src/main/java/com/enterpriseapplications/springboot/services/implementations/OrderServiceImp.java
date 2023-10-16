package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImp extends GenericServiceImp<Order,OrderDto> implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    public OrderServiceImp(OrderDao orderDao,UserDao userDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        super(modelMapper,Order.class,OrderDto.class,pagedResourcesAssembler);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public PagedModel<OrderDto> getOrders(Pageable pageable) {
        Page<Order> orders = this.orderDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(orders,modelAssembler);
    }

    @Override
    public PagedModel<OrderDto> getOrders(UUID userID, Pageable pageable) {
        Page<Order> orders = this.orderDao.getOrders(userID,pageable);
        return this.pagedResourcesAssembler.toModel(orders,modelAssembler);
    }

    @Override
    public OrderDto getOrderByProduct(UUID productID) {
        Order order = this.orderDao.findOrder(productID).orElseThrow();
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    public OrderDto getOrder(UUID orderID) {
        Order order = this.orderDao.findById(orderID).orElseThrow();
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
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
    public void deleteOrder(UUID orderID) {
        this.orderDao.findById(orderID).orElseThrow();
        this.orderDao.deleteById(orderID);
    }
}
