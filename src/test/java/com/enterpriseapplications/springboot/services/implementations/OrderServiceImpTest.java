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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class OrderServiceImpTest extends GenericTestImp<Order,OrderDto> {

    private OrderServiceImp orderServiceImp;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private OrderDao orderDao;

    @Override
    protected void init() {
        super.init();
        orderServiceImp = new OrderServiceImp(orderDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        User firstBuyer = User.builder().id(UUID.randomUUID()).build();
        User secondBuyer = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        firstElement = Order.builder().id(UUID.randomUUID()).buyer(firstBuyer).product(firstProduct).price(BigDecimal.valueOf(100)).build();
        secondElement = Order.builder().id(UUID.randomUUID()).buyer(secondBuyer).product(secondProduct).price(BigDecimal.valueOf(200)).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    public boolean valid(Order order, OrderDto orderDto) {
        Assert.assertNotNull(orderDto);
        Assert.assertEquals(order.getId(),orderDto.getId());
        Assert.assertEquals(order.getProduct().getId(),orderDto.getProduct().getId());
        Assert.assertEquals(order.getPrice(),orderDto.getPrice());
        Assert.assertEquals(order.getCreatedDate(),orderDto.getCreatedDate());
        return true;
    }
    @Test
    void getOrders() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.orderDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OrderDto> orders = this.orderServiceImp.getOrders(pageRequest);
        Assert.assertTrue(compare(elements,orders.getContent().stream().toList()));
    }

    @Test
    void getOrderByProduct() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.orderDao.findOrder(product.getId())).willReturn(Optional.of(firstElement));
        OrderDto order = this.orderServiceImp.getOrderByProduct(product.getId());
        Assert.assertTrue(valid(firstElement,order));
    }

    @Test
    void getOrder() {
        given(this.orderDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.orderDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        OrderDto firstOrder = this.orderServiceImp.getOrder(this.firstElement.getId());
        OrderDto secondOrder = this.orderServiceImp.getOrder(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstOrder));
        Assert.assertTrue(valid(this.secondElement,secondOrder));
    }

    @Test
    void createOrder() {
    }
}