package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface LikeDao extends JpaRepository<Like, UUID> {

    @Query("select l from Like l where l.user.id = :requiredUserID and l.product.id = :requiredProductID")
    Optional<Like> getLikeByProductAndUser(@Param("requiredUserID") UUID userID,@Param("requiredProductID") UUID productID);
    @Query("select l from Like l where l.user.id = :requiredUserID")
    Page<Like> getLikesByUser(@Param("requiredUserID") UUID userID, Pageable pageable);
    @Query("select l from Like l where l.product.id = :requiredProductID")
    Page<Like> getLikesByProduct(@Param("requiredProductID") UUID productID,Pageable pageable);
}
