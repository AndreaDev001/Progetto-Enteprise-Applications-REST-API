package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;
import java.util.UUID;

public interface FollowService {
    PagedModel<FollowDto> getFollows(Pageable pageable);
    PagedModel<FollowDto> findAllFollowers(UUID userID, Pageable pageable);
    PagedModel<FollowDto> findAllFollowed(UUID userID,Pageable pageable);
    FollowDto findFollow(UUID followerID,UUID followedID);
    FollowDto createFollow(UUID followedID);
    void deleteFollows(UUID followID);
}
