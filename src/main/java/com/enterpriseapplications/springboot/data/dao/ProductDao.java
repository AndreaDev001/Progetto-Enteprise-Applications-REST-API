package com.enterpriseapplications.springboot.data.dao;

import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductStatus;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
public interface ProductDao extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product>
{
    @Query("select p from PRODUCTS p where p.seller.id = :requiredID and p.visibility = :requiredVisibility order by p.createdDate")
    Page<Product> getProductsBySeller(@Param("requiredID") UUID sellerID,@Param("requiredVisibility") ProductVisibility productVisibility, Pageable pageable);
    @Query("select p from PRODUCTS p where p.visibility = :requiredVisibility and p.status = :requiredStatus order by p.createdDate desc")
    Page<Product> getRecentlyCreatedProducts(@Param("requiredVisibility") ProductVisibility productVisibility, @Param("requiredStatus") ProductStatus status, Pageable pageable);
    @Query("select p from PRODUCTS p,Like l where p.id = l.product.id and p.visibility = :requiredVisibility and p.status = :requiredStatus group by p.id order by count(p.id) desc")
    Page<Product> getMostLikedProducts(@Param("requiredVisibility") ProductVisibility productVisibility,@Param("requiredStatus") ProductStatus status,Pageable pageable);
    @Query("select p from PRODUCTS p where p.visibility = :requiredVisibility and p.status = :requiredStatus order by p.price desc")
    Page<Product> getMostExpensiveProducts(@Param("requiredVisibility") ProductVisibility productVisibility,@Param("requiredStatus") ProductStatus status,Pageable pageable);
}
