package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReportService
{
    Page<ProductReportDto> getReports(Long productID, Pageable pageable);
    void deleteProductReport(Long productReportID);
}
