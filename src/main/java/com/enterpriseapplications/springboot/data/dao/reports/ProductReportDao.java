package com.enterpriseapplications.springboot.data.dao.reports;

import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductReportDao extends JpaRepository<ProductReport, UUID> {
    @Query("select p from ProductReport p where p.product.id = :requiredID")
    Page<ProductReport> getProductReports(@Param("requiredID") UUID productID, Pageable pageable);
}
