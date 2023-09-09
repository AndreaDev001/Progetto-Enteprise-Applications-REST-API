package com.enterpriseapplications.springboot.data.dao.reports;

import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDao extends JpaRepository<Report,Long> {

    @Query("select r from Report r where r.reporter.id = :requiredID")
    Page<Report> getCreatedReports(@Param("requiredID") Long reporterID, Pageable pageable);

    @Query("select r from Report r where r.reporter.id = :requiredID")
    Page<Report> getReceivedReports(@Param("requiredID") Long reportedID, Pageable pageable);

    @Query("select r from Report r where r.reason = :requiredReason")
    Page<Report> getReportsByReason(@Param("requiredReason") ReportReason reason,Pageable pageable);
    @Query("select r from Report r where r.type = :requiredType")
    Page<Report> getReportsByType(@Param("requiredType") ReportType type,Pageable pageable);
}
