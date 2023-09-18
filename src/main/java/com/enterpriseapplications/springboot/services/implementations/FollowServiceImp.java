package com.enterpriseapplications.springboot.services.implementations;

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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImp implements FollowService {

    private final FollowDao followDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;


    @Override
    public Page<FollowDto> findAllFollowers(Long userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollowers(userID,pageable);
        return new PageImpl<>(follows.stream().map(follow -> this.modelMapper.map(follow,FollowDto.class)).collect(Collectors.toList()),pageable,follows.getTotalElements());
    }
    @Override
    public Page<FollowDto> findAllFollowed(Long userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollows(userID,pageable);
        return new PageImpl<>(follows.stream().map(follow -> this.modelMapper.map(follow,FollowDto.class)).collect(Collectors.toList()),pageable,follows.getTotalElements());
    }
    @Override
    public FollowDto findFollow(Long followerID, Long followedID) {
        return this.modelMapper.map(this.followDao.findFollow(followerID,followedID),FollowDto.class);
    }

    @Override
    @Transactional
    public FollowDto createFollow(Long followedID) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
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
    public void deleteFollows(Long followId) {
        this.followDao.findById(followId);
        this.followDao.deleteById(followId);
    }
}
