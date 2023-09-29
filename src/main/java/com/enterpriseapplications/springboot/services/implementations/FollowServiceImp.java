package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.FollowDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.FollowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FollowServiceImp implements FollowService {

    private final FollowDao followDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Follow,FollowDto> modelAssembler;
    private final PagedResourcesAssembler<Follow> pagedResourcesAssembler;


    public FollowServiceImp(FollowDao followDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Follow> pagedResourcesAssembler) {
        this.followDao = followDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Follow.class,FollowDto.class,modelMapper);
    }

    @Override
    public PagedModel<FollowDto> getFollows(Pageable pageable) {
        Page<Follow> follows = this.followDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(follows,modelAssembler);
    }

    @Override
    public PagedModel<FollowDto> findAllFollowers(UUID userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollowers(userID,pageable);
        return pagedResourcesAssembler.toModel(follows,modelAssembler);
    }
    @Override
    public PagedModel<FollowDto> findAllFollowed(UUID userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollows(userID,pageable);
        return pagedResourcesAssembler.toModel(follows,modelAssembler);
    }
    @Override
    public FollowDto findFollow(UUID followerID, UUID followedID) {
        return this.modelMapper.map(this.followDao.findFollow(followerID,followedID),FollowDto.class);
    }

    @Override
    @Transactional
    public FollowDto createFollow(UUID followedID) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User followed = this.userDao.findById(followedID).orElseThrow();
        if(requiredUser.getId().equals(followedID))
            throw new InvalidFormat("errors.follows.invalidFollower");
        Follow follow = new Follow();
        follow.setFollower(requiredUser);
        follow.setFollowed(followed);
        this.followDao.save(follow);
        return this.modelMapper.map(follow,FollowDto.class);
    }

    @Override
    @Transactional
    public void deleteFollows(UUID followId) {
        this.followDao.findById(followId);
        this.followDao.deleteById(followId);
    }
}
