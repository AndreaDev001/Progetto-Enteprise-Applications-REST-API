package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productReports")
@RequiredArgsConstructor
public class ProductReportController {
    private final ProductReportService productReportService;
}
