package com.enterpriseapplications.springboot.data.dao.images;


import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ProductImageDao extends JpaRepository<ProductImage, UUID>
{
    @Query("select p from ProductImage p where p.product.id = :requiredID")
    List<ProductImage> getProductImages(@Param("requiredID") UUID productID);
}
