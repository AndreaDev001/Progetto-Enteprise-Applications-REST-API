package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.FollowDao;
import com.enterpriseapplications.springboot.data.entities.Follow;
import com.enterpriseapplications.springboot.services.interfaces.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImp implements FollowService {

    private final FollowDao followDao;

    @Override
    public Page<Follow> findAllFollowers(Long userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollowers(userID,pageable);
        return new PageImpl<>(follows.toList(),pageable,follows.getTotalElements());
    }
    @Override
    public Page<Follow> findAllFollowed(Long userID, Pageable pageable) {
        Page<Follow> follows = this.followDao.findAllFollows(userID,pageable);
        return new PageImpl<>(follows.toList(),pageable,follows.getTotalElements());
    }
    @Override
    public Optional<Follow> findFollow(Long followerID, Long followedID) {
        return this.followDao.findFollow(followerID,followedID);
    }
}
