package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.*;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.*;
import com.enterpriseapplications.springboot.data.entities.enums.OrderStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.services.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImp extends GenericServiceImp<Order,OrderDto> implements OrderService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss");
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final PaymentMethodDao paymentMethodDao;
    private final AddressDao addressDao;

    public OrderServiceImp(OrderDao orderDao,UserDao userDao,PaymentMethodDao paymentMethodDao,AddressDao addressDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        super(modelMapper,Order.class,OrderDto.class,pagedResourcesAssembler);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
        this.paymentMethodDao = paymentMethodDao;
        this.addressDao = addressDao;
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
    public List<OrderDto> getOrders(OrderStatus status) {
        List<Order> orders = this.orderDao.getOrders(status);
        return orders.stream().map(order -> this.modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
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
        Address requiredAddress = this.addressDao.findById(createOrderDto.getAddressID()).orElseThrow();
        PaymentMethod requiredPaymentMethod = this.paymentMethodDao.findById(createOrderDto.getPaymentMethodID()).orElseThrow();
        if(requiredUser.getId().equals(requiredProduct.getSeller().getId()))
            throw new InvalidFormat("error.order.invalidBuyer");
        if(requiredProduct.getStatus() == ProductStatus.BOUGHT)
            throw new InvalidFormat("error.order.alreadyBought");
        Order order = new Order();
        order.setBuyer(requiredUser);
        order.setProduct(requiredProduct);
        order.setPrice(createOrderDto.getPrice());
        order.setAddress(requiredAddress);
        order.setPaymentMethod(requiredPaymentMethod);
        order.setStatus(OrderStatus.PROCESSING);
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(3);
        requiredProduct.setStatus(ProductStatus.BOUGHT);
        order.setDeliveryDate(localDate);
        requiredProduct.setStatus(ProductStatus.BOUGHT);
        this.productDao.save(requiredProduct);
        order = this.orderDao.save(order);
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto updateOrder(UpdateOrderDto updateOrderDto) {
        Order order = this.orderDao.findById(updateOrderDto.getOrderID()).orElseThrow();
        if(updateOrderDto.getPrice() != null)
            order.setPrice(updateOrderDto.getPrice());
        return this.modelMapper.map(order,OrderDto.class);
    }

    @Override
    public OrderStatus[] getStatues() {
        return OrderStatus.values();
    }

    @Override
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000,initialDelay = 24 * 60 * 60 * 1000)
    public void updateProcessing() {
        List<Order> requiredOrders = this.orderDao.getOrders(OrderStatus.PROCESSING);
        requiredOrders.forEach(order -> {
            log.info(String.format("Order [%s] has been processed at [%s]"),order.getId().toString(),dateTimeFormatter.format(LocalDateTime.now()));
            order.setStatus(OrderStatus.SHIPPING);
        });
        this.orderDao.saveAll(requiredOrders);
    }

    @Override
    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void updateShipping() {
        List<Order> requiredOrders = this.orderDao.getDeliveredOrders(LocalDate.now());
        requiredOrders.forEach(order -> {
            log.info(String.format("Order [%s] has been delivered at [%s]"),order.getId().toString(),dateTimeFormatter.format(LocalDateTime.now()));
            order.setStatus(OrderStatus.DELIVERED);
        });
        this.orderDao.saveAll(requiredOrders);
    }

    @Override
    @Transactional
    public void deleteOrder(UUID orderID) {
        this.orderDao.findById(orderID).orElseThrow();
        this.orderDao.deleteById(orderID);
    }
}
