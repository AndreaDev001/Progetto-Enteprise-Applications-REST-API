package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface FollowService {
    PagedModel<FollowDto> findAllFollowers(Long userID, Pageable pageable);
    PagedModel<FollowDto> findAllFollowed(Long userID,Pageable pageable);
    FollowDto findFollow(Long followerID,Long followedID);
    FollowDto createFollow(Long followedID);
    void deleteFollows(Long followID);
}
