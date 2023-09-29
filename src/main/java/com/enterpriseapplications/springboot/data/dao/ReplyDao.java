package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Reply;
import com.enterpriseapplications.springboot.data.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReplyDao extends JpaRepository<Reply,UUID>
{
    @Query("select r from Reply r where r.review = :requiredID")
    Optional<Reply> findByReview(@Param("requiredID") UUID id);
    @Query("select r from Reply r where r.writer = :requiredID")
    Page<Reply> findByWriter(@Param("requiredID") UUID requiredID, Pageable pageable);
}
