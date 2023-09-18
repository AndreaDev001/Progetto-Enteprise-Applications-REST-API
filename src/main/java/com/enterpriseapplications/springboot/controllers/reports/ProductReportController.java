package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productReports")
@RequiredArgsConstructor
public class ProductReportController {

    private final ProductReportService productReportService;

    @GetMapping("{productID}")
    public ResponseEntity<PaginationResponse<ProductReportDto>> getProductReports(@PathVariable("productID") Long productID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ProductReportDto> productReports = this.productReportService.getReports(productID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(productReports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),productReports.getTotalPages(),productReports.getTotalElements()));
    }

    @PostMapping("{productID}")
    public ResponseEntity<ProductReportDto> createProductReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("productID") @PositiveOrZero Long productID) {
        return ResponseEntity.ok(this.productReportService.createProductReport(createReportDto,productID));
    }

    @DeleteMapping("{productReportID}")
    public ResponseEntity<Void> deleteProductReport(@PathVariable("productReportID") Long productReportID) {
        this.productReportService.deleteProductReport(productReportID);
        return ResponseEntity.noContent().build();
    }
}
