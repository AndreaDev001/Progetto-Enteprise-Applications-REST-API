package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowDao extends JpaRepository<Follow,Long> {

    @Query("select f from Follow f where f.followed.id = :requiredID")
    Page<Follow> findAllFollowers(@Param("requiredID") Long id);

    @Query("select f from Follow f where f.follower.id = :requiredID")
    Page<Follow> findAllFollows(@Param("requiredID") Long id);
}
