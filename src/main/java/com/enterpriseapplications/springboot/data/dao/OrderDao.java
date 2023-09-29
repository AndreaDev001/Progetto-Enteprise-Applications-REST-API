package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDao extends JpaRepository<Order,UUID> {
    @Query("select o from Order o where o.buyer.id = :requiredID")
    Page<Order> getOrders(@Param("requiredID") UUID buyerID, Pageable pageable);
    @Query("select o from Order o where o.product.id = :requiredID")
    Optional<Order> findOrder(@Param("requiredID") UUID productID);
}
