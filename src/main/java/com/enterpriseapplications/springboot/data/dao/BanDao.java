package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BanDao extends JpaRepository<Ban, UUID>, JpaSpecificationExecutor<Ban>
{

    @Query("select b from Ban b where b.banner.id = :requiredID")
    Page<Ban> getCreatedBans(@Param("requiredID") UUID userID,Pageable pageable);

    @Query("select b from Ban b where b.banned.id = :requiredID")
    Page<Ban> getReceivedBans(@Param("requiredID") UUID userID,Pageable pageable);

    @Query("select b from Ban b where b.banned.id = :requiredID and b.expired = false")
    Optional<Ban> findBan(@Param("requiredID") UUID bannedID);

    @Query("select b from Ban b where b.reason = :requiredReason")
    Page<Ban> getBansByReason(@Param("requiredReason")ReportReason reason, Pageable pageable);
}
