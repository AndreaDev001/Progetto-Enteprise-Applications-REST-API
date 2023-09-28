package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Offer;
import com.enterpriseapplications.springboot.data.entities.enums.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferDao extends JpaRepository<Offer,Long>, JpaSpecificationExecutor<Offer>
{
    @Query("select o from Offer o where o.status = :requiredStatus")
    Page<Offer> getOffersByStatus(@Param("requiredStatus")OfferStatus status,Pageable pageable);
    @Query("select o from Offer o where o.expired = :requiredExpired")
    Page<Offer> getOffersByExpired(@Param("requiredExpired") boolean expired,Pageable pageable);
    @Query("select o from Offer o where o.buyer.id = :requiredID")
    Page<Offer> getOffersByBuyerID(@Param("requiredID") Long requiredID, Pageable pageable);
    @Query("select o from Offer o where o.product.id = :requiredID")
    Page<Offer> getOffersByProductID(@Param("requiredID") Long requiredID,Pageable pageable);
    @Query("select o from Offer  o where o.product.id = :requiredID and o.status = :requiredStatus")
    Page<Offer> getOffersByProductAndStatus(@Param("requiredID") Long offerID,@Param("requiredStatus") OfferStatus status,Pageable pageable);
}
