package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.ConversationDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import com.enterpriseapplications.springboot.data.entities.Conversation;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.ConversationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImp extends GenericServiceImp<Conversation,ConversationDto> implements ConversationService
{
    private final ConversationDao conversationDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public ConversationServiceImp(UserDao userDao,ProductDao productDao,ConversationDao conversationDao,ModelMapper modelMapper,PagedResourcesAssembler<Conversation> pagedResourcesAssembler) {
        super(modelMapper,Conversation.class,ConversationDto.class,pagedResourcesAssembler);
        this.userDao = userDao;
        this.conversationDao = conversationDao;
        this.productDao = productDao;
    }

    @Override
    public PagedModel<ConversationDto> getConversations(Pageable pageable) {
        Page<Conversation> conversations = this.conversationDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(conversations,modelAssembler);
    }

    @Override
    public ConversationDto find(UUID id) {
        Conversation conversation= this.conversationDao.findById(id).orElseThrow();
        return this.modelMapper.map(conversation,ConversationDto.class);
    }

    @Override
    public List<ConversationDto> getConversationsByStarter(UUID starter) {
        List<Conversation> conversations = this.conversationDao.getConversationByStarter(starter);
        return conversations.stream().map(conversation -> this.modelMapper.map(conversation,ConversationDto.class)).collect(Collectors.toList());
    }

    @Override
    public ConversationDto getConversation(UUID starter,UUID productID) {
        Conversation conversation = this.conversationDao.getConversation(starter,productID).orElseThrow();
        return this.modelMapper.map(conversation,ConversationDto.class);
    }

    @Override
    public List<ConversationDto> getConversations(UUID userID) {
        List<Conversation> conversations = this.conversationDao.getConversations(userID);
        return conversations.stream().map(conversation -> this.modelMapper.map(conversation,ConversationDto.class)).collect(Collectors.toList());
    }

    @Override
    public PagedModel<ConversationDto> getConversationByProduct(UUID productID, Pageable pageable) {
        Page<Conversation> conversations = this.conversationDao.getConversationByProduct(productID,pageable);
        return this.pagedResourcesAssembler.toModel(conversations,modelAssembler);
    }

    @Override
    @Transactional
    public ConversationDto createConversation(CreateConversationDto createConversationDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(createConversationDto.getProductID()).orElseThrow();
        if(requiredUser.getId().equals(requiredProduct.getSeller().getId()))
            throw new InvalidFormat("errors.conversation.invalidStarter");
        Conversation conversation = Conversation.builder().product(requiredProduct)
                .starter(requiredUser).build();
        conversation = this.conversationDao.save(conversation);
        return this.modelMapper.map(conversation,ConversationDto.class);
    }

    @Override
    @Transactional
    public void deleteConversation(UUID id) {
        this.conversationDao.findById(id).orElseThrow();
        this.conversationDao.deleteById(id);
    }
}
