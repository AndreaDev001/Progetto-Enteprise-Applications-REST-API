package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.LikeDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.LikeDto;
import com.enterpriseapplications.springboot.data.entities.Like;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class LikeServiceImpTest extends GenericTestImp<Like, LikeDto> {

    private LikeServiceImp likeServiceImp;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private LikeDao likeDao;


    @Override
    protected void init() {
        super.init();
        likeServiceImp = new LikeServiceImp(likeDao,productDao,userDao,modelMapper,pagedResourcesAssembler);
        User user = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).build();
        firstElement = Like.builder().id(UUID.randomUUID()).user(user).product(product).build();
        secondElement = Like.builder().id(UUID.randomUUID()).user(user).product(product).build();
        elements = List.of(firstElement,secondElement);
    }

    @Before
    public void before() {
        this.init();
    }


    @Test
    public void getLikes() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.likeDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<LikeDto> pagedModel = this.likeServiceImp.getLikes(pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void getLikesByProduct() {
        PageRequest pageRequest = PageRequest.of(0,20);
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.likeDao.getLikesByProduct(product.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<LikeDto> pagedModel = this.likeServiceImp.getLikesByProduct(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void getLikesByUser() {
        PageRequest pageRequest = PageRequest.of(0,20);
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.likeDao.getLikesByUser(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<LikeDto> pagedModel = this.likeServiceImp.getLikesByUser(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,pagedModel.getContent().stream().toList()));
        Assert.assertTrue(validPage(pagedModel,20,0,1,2));
    }

    @Test
    public void getLike() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.likeDao.getLikeByProductAndUser(firstUser.getId(),product.getId())).willReturn(Optional.of(firstElement));
        given(this.likeDao.getLikeByProductAndUser(secondUser.getId(),product.getId())).willReturn(Optional.of(secondElement));
        LikeDto firstLike = this.likeServiceImp.getLike(firstUser.getId(),product.getId());
        LikeDto secondLike = this.likeServiceImp.getLike(secondUser.getId(),product.getId());
        Assert.assertTrue(valid(firstElement,firstLike));
        Assert.assertTrue(valid(secondElement,secondLike));
    }

    @Test
    public void getLikeById() {
        UUID firstRandomID = UUID.randomUUID();
        UUID secondRandomID =  UUID.randomUUID();
        given(this.likeDao.findById(firstRandomID)).willReturn(Optional.of(firstElement));
        given(this.likeDao.findById(secondRandomID)).willReturn(Optional.of(secondElement));
        LikeDto firstLike = this.likeServiceImp.getLike(firstRandomID);
        LikeDto secondLike = this.likeServiceImp.getLike(secondRandomID);
        Assert.assertTrue(valid(firstElement,firstLike));
        Assert.assertTrue(valid(secondElement,secondLike));
    }

    @Override
    protected boolean valid(Like entity, LikeDto dto) {
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getId(),dto.getId());
        Assert.assertEquals(entity.getUser().getId(),dto.getUser().getId());
        Assert.assertEquals(entity.getProduct().getId(),dto.getProduct().getId());
        Assert.assertEquals(entity.getCreatedDate(),dto.getCreatedDate());
        return true;
    }
}
