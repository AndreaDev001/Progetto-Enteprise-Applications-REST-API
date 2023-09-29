package com.enterpriseapplications.springboot.data.dao;


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
public interface ReviewDao extends JpaRepository<Review,UUID> {

    @Query("select r from Review r where r.writer.id = :requiredID")
    Page<Review> findAllWrittenReviews(@Param("requiredID") UUID writerID, Pageable pageable);
    @Query("select r from Review r where r.receiver.id = :requiredID")
    Page<Review> findAllReviewsReceived(@Param("requiredID") UUID receiverID, Pageable pageable);
    @Query("select r from Review r where r.writer.id = :requiredWriterID and r.receiver.id = :requiredReceiverID")
    Optional<Review> findReview(@Param("requiredWriterID") UUID writerID,@Param("requiredReceiverID") UUID receiverID);
}
