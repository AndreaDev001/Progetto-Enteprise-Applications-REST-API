package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductDao extends JpaRepository<Product,Long>
{
    @Query("select p from PRODUCTS p where p.seller.id = :requiredID")
    Page<Product> getProductsBySeller(@Param("requiredID") Long sellerID, Pageable pageable);
}
