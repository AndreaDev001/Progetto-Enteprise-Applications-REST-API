package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.*;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOrderDto;
import com.enterpriseapplications.springboot.data.dto.output.OrderDto;
import com.enterpriseapplications.springboot.data.entities.*;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import com.enterpriseapplications.springboot.services.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
    @Mock
    private AddressDao addressDao;
    @Mock
    private PaymentMethodDao paymentMethodDao;
    @Override
    protected void init() {
        super.init();
        orderServiceImp = new OrderServiceImp(orderDao,userDao,paymentMethodDao,addressDao,productDao,modelMapper,pagedResourcesAssembler);
        User firstBuyer = User.builder().id(UUID.randomUUID()).build();
        User secondBuyer = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).seller(secondBuyer).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).seller(firstBuyer).build();
        Address firstAddress = Address.builder().id(UUID.randomUUID()).user(firstBuyer).build();
        Address secondAddress = Address.builder().id(UUID.randomUUID()).user(secondBuyer).build();
        PaymentMethod firstPaymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).user(firstBuyer).build();
        PaymentMethod secondPaymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).user(secondBuyer).build();
        TestUtils.generateValues(firstBuyer);
        TestUtils.generateValues(secondBuyer);
        TestUtils.generateValues(firstProduct);
        TestUtils.generateValues(secondProduct);
        TestUtils.generateValues(firstAddress);
        TestUtils.generateValues(secondAddress);
        TestUtils.generateValues(firstPaymentMethod);
        TestUtils.generateValues(secondPaymentMethod);
        firstElement = Order.builder().id(UUID.randomUUID()).buyer(firstBuyer).paymentMethod(firstPaymentMethod).address(firstAddress).product(firstProduct).price(BigDecimal.valueOf(100)).build();
        secondElement = Order.builder().id(UUID.randomUUID()).buyer(secondBuyer).paymentMethod(secondPaymentMethod).address(secondAddress).product(secondProduct).price(BigDecimal.valueOf(200)).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
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
        Assert.assertTrue(validPage(orders,20,0,1,2));
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
        User user = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).seller(user).build();
        Address address = Address.builder().id(UUID.randomUUID()).build();
        PaymentMethod paymentMethod = PaymentMethod.builder().id(UUID.randomUUID()).build();
        CreateOrderDto createOrderDto = CreateOrderDto.builder().productID(product.getId()).addressID(address.getId()).paymentMethodID(paymentMethod.getId()).price(new BigDecimal(100)).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.addressDao.findById(address.getId())).willReturn(Optional.of(address));
        given(this.paymentMethodDao.findById(paymentMethod.getId())).willReturn(Optional.of(paymentMethod));
        given(this.productDao.findById(product.getId())).willReturn(Optional.of(product));
        given(this.orderDao.save(any(Order.class))).willReturn(firstElement);
        OrderDto orderDto = this.orderServiceImp.createOrder(createOrderDto);
        Assert.assertTrue(valid(firstElement,orderDto));
    }
}