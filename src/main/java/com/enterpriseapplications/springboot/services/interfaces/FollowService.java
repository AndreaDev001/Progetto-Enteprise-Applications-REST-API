package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowService {
    Page<Follow> findAllFollowers(Long userID,Pageable pageable);
    Page<Follow> findAllFollowed(Long userID,Pageable pageable);
    Optional<Follow> findFollow(Long followerID,Long followedID);
}
