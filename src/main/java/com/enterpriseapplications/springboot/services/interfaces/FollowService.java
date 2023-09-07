package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowService {
    Page<FollowDto> findAllFollowers(Long userID, Pageable pageable);
    Page<FollowDto> findAllFollowed(Long userID,Pageable pageable);
    FollowDto findFollow(Long followerID,Long followedID);
    void deleteFollows(Long followId);
}
