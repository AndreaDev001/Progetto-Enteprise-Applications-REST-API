package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.OrderDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.Order;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class OrderServiceImpTest {

    private OrderServiceImp orderServiceImp;
    private Order firstOrder;
    private Order secondOrder;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private OrderDao orderDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        orderServiceImp = new OrderServiceImp(orderDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        User firstBuyer = User.builder().id(UUID.randomUUID()).build();
        User secondBuyer = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        firstOrder = Order.builder().id(UUID.randomUUID()).buyer(firstBuyer).product(firstProduct).price(BigDecimal.valueOf(100)).build();
        secondOrder = Order.builder().id(UUID.randomUUID()).buyer(secondBuyer).product(secondProduct).price(BigDecimal.valueOf(200)).build();

    }

    boolean valid(Order order, OrderDto orderDto) {
        Assert.assertNotNull(orderDto);
        Assert.assertEquals(order.getId(),orderDto.getId());
        Assert.assertEquals(order.getProduct().getId(),orderDto.getProduct().getId());
        Assert.assertEquals(order.getPrice(),orderDto.getPrice());
        Assert.assertEquals(order.getCreatedDate(),orderDto.getCreatedDate());
        return true;
    }
    @Test
    void getOrders() {
    }

    @Test
    void testGetOrders() {
    }

    @Test
    void getOrderByProduct() {
    }

    @Test
    void getOrder() {
        given(this.orderDao.findById(firstOrder.getId())).willReturn(Optional.of(firstOrder));
        given(this.orderDao.findById(secondOrder.getId())).willReturn(Optional.of(secondOrder));
        OrderDto firstOrder = this.orderServiceImp.getOrder(this.firstOrder.getId());
        OrderDto secondOrder = this.orderServiceImp.getOrder(this.secondOrder.getId());
        Assert.assertTrue(valid(this.firstOrder,firstOrder));
        Assert.assertTrue(valid(this.secondOrder,secondOrder));
    }

    @Test
    void createOrder() {
    }
}