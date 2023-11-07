package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.OfferDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.specifications.OfferSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateOfferDto;
import com.enterpriseapplications.springboot.data.dto.output.OfferDto;
import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class OfferServiceImpTest extends GenericTestImp<Offer,OfferDto> {

    private OfferServiceImp offerServiceImp;
    @Mock
    public OfferDao offerDao;
    @Mock
    public UserDao userDao;
    @Mock
    public ProductDao productDao;

    @Override
    protected void init() {
        super.init();
        offerServiceImp = new OfferServiceImp(offerDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).seller(User.builder().id(UUID.randomUUID()).build()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).seller(User.builder().id(UUID.randomUUID()).build()).build();
        TestUtils.generateValues(firstUser);
        TestUtils.generateValues(secondUser);
        TestUtils.generateValues(firstProduct);
        TestUtils.generateValues(secondProduct);
        firstElement = Offer.builder().id(UUID.randomUUID()).description("description").price(BigDecimal.valueOf(100)).status(OfferStatus.OPEN).buyer(firstUser).product(firstProduct).build();
        secondElement = Offer.builder().id(UUID.randomUUID()).description("description").price(BigDecimal.valueOf(200)).status(OfferStatus.ACCEPTED).buyer(secondUser).product(secondProduct).build();
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }

    public boolean valid(Offer offer, OfferDto offerDto) {
        Assert.assertNotNull(offerDto);
        Assert.assertEquals(offer.getId(),offerDto.getId());
        Assert.assertEquals(offer.getDescription(),offerDto.getDescription());
        Assert.assertEquals(offer.getPrice(),offerDto.getPrice());
        Assert.assertEquals(offer.getStatus(),offerDto.getStatus());
        Assert.assertEquals(offer.getBuyer().getId(),offerDto.getBuyer().getId());
        Assert.assertEquals(offer.getProduct().getId(),offerDto.getProduct().getId());
        Assert.assertEquals(offer.getExpirationDate(),offerDto.getExpirationDate());
        Assert.assertEquals(offer.isExpired(),offerDto.isExpired());
        Assert.assertEquals(offer.getCreatedDate(),offerDto.getCreatedDate());
        return true;
    }

    @Test
    void getOffer() {
        given(this.offerDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.offerDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        OfferDto firstOffer = this.offerServiceImp.getOffer(firstElement.getId());
        OfferDto secondOffer = this.offerServiceImp.getOffer(secondElement.getId());
        Assert.assertTrue(valid(firstElement,firstOffer));
        Assert.assertTrue(valid(secondElement,secondOffer));
    }

    @Test
    void getOffers() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffers(pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersByStatus() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getOffersByStatus(OfferStatus.OPEN,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffersByStatus(OfferStatus.OPEN,pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersByExpired() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getOffersByExpired(true,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffersByExpired(true,pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersByBuyerID() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getOffersByBuyerID(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffersByBuyerID(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersByProductID() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getOffersByProductID(product.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffersByProductID(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersByProductIDAndStatus() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getOffersByProductAndStatus(product.getId(),OfferStatus.OPEN,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> offers = this.offerServiceImp.getOffersByProductIDAndStatus(product.getId(),OfferStatus.OPEN,pageRequest);
        Assert.assertTrue(compare(elements,offers.getContent().stream().toList()));
    }

    @Test
    void getOffersBySpec() {
        OfferSpecifications.Filter filter = new OfferSpecifications.Filter();
        PageRequest pageRequest = PageRequest.of(0,20);
        Specification<Offer> specification = OfferSpecifications.withFilter(filter);
        given(this.offerDao.findAll(specification,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> pagedModel = this.offerServiceImp.getOffersBySpec(specification,pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    void createOffer() {
        User user = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).price(BigDecimal.valueOf(3000)).minPrice(BigDecimal.valueOf(100)).build();
        product.setSeller(user);
        CreateOfferDto createOfferDto = CreateOfferDto.builder().description("description").price(new BigDecimal(100)).productID(product.getId()).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.productDao.findById(product.getId())).willReturn(Optional.of(product));
        given(this.offerDao.save(any(Offer.class))).willReturn(firstElement);
        OfferDto offerDto = this.offerServiceImp.createOffer(createOfferDto);
        Assert.assertTrue(valid(firstElement,offerDto));
    }


    @Test
    public void getCreatedOffers() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getCreatedOffers(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> pagedModel = this.offerServiceImp.getCreatedOffers(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void getReceivedOffers() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.offerDao.getReceivedOffers(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<OfferDto> pagedModel = this.offerServiceImp.getReceivedOffers(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }
}