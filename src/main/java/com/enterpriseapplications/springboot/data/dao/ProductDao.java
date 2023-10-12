package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ProductDao extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product>
{
    @Query("select p from PRODUCTS p where p.seller.id = :requiredID and p.visibility = :requiredVisibility order by p.createdDate")
    Page<Product> getProductsBySeller(@Param("requiredID") UUID sellerID,@Param("requiredVisibility") ProductVisibility productVisibility, Pageable pageable);
    @Query("select p from PRODUCTS p where p.visibility = :requiredVisibility order by p.createdDate desc")
    Page<Product> getRecentlyCreatedProducts(@Param("requiredVisibility") ProductVisibility productVisibility,Pageable pageable);
    @Query("select p from PRODUCTS p where p.visibility = :requiredVisibility")
    Page<Product> getMostLikedProducts(@Param("requiredVisibility") ProductVisibility productVisibility,Pageable pageable);
    @Query("select p from PRODUCTS p where p.visibility = :requiredVisibility order by p.price desc")
    Page<Product> getMostExpensiveProducts(@Param("requiredVisibility") ProductVisibility productVisibility,Pageable pageable);
}
