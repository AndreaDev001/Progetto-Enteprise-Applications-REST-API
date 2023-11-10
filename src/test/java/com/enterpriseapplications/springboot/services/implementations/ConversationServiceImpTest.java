package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.ConversationDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import com.enterpriseapplications.springboot.data.entities.Conversation;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ConversationServiceImpTest extends GenericTestImp<Conversation, ConversationDto> {

    private ConversationServiceImp conversationServiceImp;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private ConversationDao conversationDao;


    @Override
    protected void init() {
        super.init();
        conversationServiceImp = new ConversationServiceImp(userDao,productDao,conversationDao,modelMapper,pagedResourcesAssembler);
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).seller(secondUser).receivedLikes(new HashSet<>()).conversations(new HashSet<>()).build();

        firstElement = Conversation.builder().id(UUID.randomUUID()).starter(firstUser).product(product).build();
        secondElement = Conversation.builder().id(UUID.randomUUID()).starter(firstUser).product(product).build();
        elements = List.of(firstElement,secondElement);
    }

    @Before
    public void before() {
        this.init();
        this.defaultBefore();
    }

    @Override
    protected boolean valid(Conversation entity, ConversationDto dto) {
        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getId(),dto.getId());
        Assert.assertEquals(entity.getStarter().getId(),dto.getStarter().getId());
        Assert.assertEquals(entity.getProduct().getId(),dto.getProduct().getId());
        Assert.assertEquals(entity.getCreatedDate(),dto.getCreatedDate());
        return true;
    }

    @Test
    public void getConversations() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.conversationDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ConversationDto> conversations = this.conversationServiceImp.getConversations(pageRequest);
        Assert.assertTrue(compare(elements,conversations.getContent().stream().toList()));
        Assert.assertTrue(validPage(conversations,20,0,1,2));
    }

    @Test
    public void find() {
        UUID firstRandomID = UUID.randomUUID();
        UUID secondRandomID = UUID.randomUUID();
        given(this.conversationDao.findById(firstRandomID)).willReturn(Optional.of(firstElement));
        given(this.conversationDao.findById(secondRandomID)).willReturn(Optional.of(secondElement));
        ConversationDto firstConversation = this.conversationServiceImp.find(firstRandomID);
        ConversationDto secondConversation = this.conversationServiceImp.find(secondRandomID);
        Assert.assertTrue(valid(firstElement,firstConversation));
        Assert.assertTrue(valid(secondElement,secondConversation));
    }

    @Test
    public void getConversationByStarter() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.conversationDao.getConversationByStarter(user.getId())).willReturn(elements);
        List<ConversationDto> conversations = this.conversationServiceImp.getConversationsByStarter(user.getId());
        Assert.assertTrue(compare(elements,conversations));
    }

    @Test
    public void getConversation() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        given(this.conversationDao.getConversation(firstUser.getId(),firstProduct.getId())).willReturn(Optional.of(firstElement));
        given(this.conversationDao.getConversation(secondUser.getId(),secondProduct.getId())).willReturn(Optional.of(secondElement));
        ConversationDto firstConversation = this.conversationServiceImp.getConversation(firstUser.getId(),firstProduct.getId());
        ConversationDto secondConversation = this.conversationServiceImp.getConversation(secondUser.getId(),secondProduct.getId());
        Assert.assertTrue(valid(firstElement,firstConversation));
        Assert.assertTrue(valid(secondElement,secondConversation));
    }

    @Test
    public void getConversationByProduct() {
        PageRequest pageRequest = PageRequest.of(0,20);
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.conversationDao.getConversationByProduct(product.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ConversationDto> conversations = this.conversationServiceImp.getConversationByProduct(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,conversations.getContent().stream().toList()));
        Assert.assertTrue(validPage(conversations,20,0,1,2));
    }

    @Test
    public void createConversation() {
        User user = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).seller(user).build();
        CreateConversationDto createConversationDto = CreateConversationDto.builder().productID(product.getId()).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.productDao.findById(product.getId())).willReturn(Optional.of(product));
        given(this.conversationDao.save(any(Conversation.class))).willReturn(firstElement);
        ConversationDto conversationDto = this.conversationServiceImp.createConversation(createConversationDto);
        Assert.assertTrue(valid(firstElement,conversationDto));
    }

    @Test
    public void getConversationsByStarter() {
        User user = User.builder().id(UUID.randomUUID()).build();
        given(this.conversationDao.getConversationByStarter(user.getId())).willReturn(elements);
        List<ConversationDto> conversations = this.conversationServiceImp.getConversationsByStarter(user.getId());
        Assert.assertTrue(compare(elements,conversations));
    }
}
