package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewDao extends JpaRepository<Review,Long> {

    @Query("select r from Review r where r.writer.id = :requiredID")
    Page<Review> findAllWrittenReviews(@Param("requiredID") Long writerID, Pageable pageable);
    @Query("select r from Review r where r.receiver.id = :requiredID")
    Page<Review> findAllReviewsReceived(@Param("requiredID") Long receiverID,Pageable pageable);
    @Query("select r from Review r where r.writer.id = :requiredWriterID and r.receiver.id = :requiredReceiverID")
    Optional<Review> findReview(@Param("requiredWriterID") Long writerID,@Param("requiredReceiverID") Long receiverID);
}
