package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dao.FollowDao;
import com.enterpriseapplications.springboot.data.dao.LikeDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.dto.output.LikeDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import com.enterpriseapplications.springboot.data.entities.Like;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.LikeService;
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
public class LikeServiceImp extends GenericServiceImp<Like,LikeDto> implements LikeService
{
    private final LikeDao likeDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Like, LikeDto> modelAssembler;
    private final PagedResourcesAssembler<Like> pagedResourcesAssembler;

    public LikeServiceImp(LikeDao likeDao,ProductDao productDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Like> pagedResourcesAssembler) {
        super(modelMapper,Like.class,LikeDto.class,pagedResourcesAssembler);
        this.productDao = productDao;
        this.likeDao = likeDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Like.class, LikeDto.class,modelMapper);
    }

    @Override
    public PagedModel<LikeDto> getLikes(Pageable pageable) {
        Page<Like> likes = this.likeDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByUser(UUID userID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByUser(userID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public PagedModel<LikeDto> getLikesByProduct(UUID productID, Pageable pageable) {
        Page<Like> likes = this.likeDao.getLikesByProduct(productID,pageable);
        return this.pagedResourcesAssembler.toModel(likes,modelAssembler);
    }

    @Override
    public LikeDto getLike(UUID likeID) {
        return this.modelMapper.map(this.likeDao.findById(likeID).orElseThrow(),LikeDto.class);
    }

    @Override
    public LikeDto getLike(UUID userID, UUID productID) {
        return this.modelMapper.map(this.likeDao.getLikeByProductAndUser(userID,productID).orElseThrow(),LikeDto.class);
    }

    @Override
    @Transactional
    public LikeDto createLike(UUID productID) {
        User user = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product product = this.productDao.findById(productID).orElseThrow();
        if(user.getId().equals(product.getSeller().getId()))
            throw new InvalidFormat("error.likes.invalidUser");
        Like like = Like.builder().product(product).user(user).build();
        this.likeDao.save(like);
        return this.modelMapper.map(like,LikeDto.class);
    }

    @Override
    @Transactional
    public void deleteLike(UUID likeID) {
        this.likeDao.findById(likeID).orElseThrow();
        this.likeDao.deleteById(likeID);
    }
}
