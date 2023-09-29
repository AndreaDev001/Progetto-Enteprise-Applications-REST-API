package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowDao extends JpaRepository<Follow, UUID> {

    @Query("select f from Follow f where f.followed.id = :requiredID")
    Page<Follow> findAllFollowers(@Param("requiredID") UUID id, Pageable pageable);
    @Query("select f from Follow f where f.follower.id = :requiredID")
    Page<Follow> findAllFollows(@Param("requiredID") UUID id,Pageable pageable);
    @Query("select f from Follow f where f.follower.id = :requiredFollowerID and f.follower.id = :requiredFollowedID")
    Optional<Follow> findFollow(@Param("requiredFollowerID") UUID followerID,@Param("requiredFollowedID") UUID followedID);
}
