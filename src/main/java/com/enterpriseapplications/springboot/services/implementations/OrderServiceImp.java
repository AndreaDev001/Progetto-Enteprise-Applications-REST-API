package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.GenericModelAssembler;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Order,OrderDto> modelAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    public OrderServiceImp(OrderDao orderDao,UserDao userDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Order.class,OrderDto.class,modelMapper);
    }

    @Override
    public PagedModel<OrderDto> getOrders(UUID userID, Pageable pageable) {
        Page<Order> orders = this.orderDao.getOrders(userID,pageable);
        return this.pagedResourcesAssembler.toModel(orders,modelAssembler);
    }

    @Override
    public OrderDto getOrder(UUID productID) {
        Order order = this.orderDao.findOrder(productID).orElseThrow();
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
