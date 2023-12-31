package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface ProductReportService
{
    ProductReportDto getReport(UUID reportID);
    PagedModel<ProductReportDto> getReports(Pageable pageable);
    PagedModel<ProductReportDto> getReports(UUID productID, Pageable pageable);
    ProductReportDto createProductReport(CreateReportDto createReportDto,UUID productID);
    ProductReportDto updateProductReport(UpdateReportDto updateReportDto);
    ProductReportDto getReport(UUID userID,UUID productID);
    void deleteProductReport(UUID productReportID);
}
