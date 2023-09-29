package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/productReports")
@RequiredArgsConstructor
public class ProductReportController {

    private final ProductReportService productReportService;


    @GetMapping
    public ResponseEntity<PagedModel<ProductReportDto>> getProductReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductReportDto> productReports = this.productReportService.getReports(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(productReports);
    }

    @GetMapping("{productID}")
    public ResponseEntity<PagedModel<ProductReportDto>> getProductReports(@PathVariable("productID") UUID productID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ProductReportDto> productReports = this.productReportService.getReports(productID, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(productReports);
    }

    @PostMapping("{productID}")
    public ResponseEntity<ProductReportDto> createProductReport(@RequestBody @Valid CreateReportDto createReportDto,@PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(this.productReportService.createProductReport(createReportDto,productID));
    }

    @PutMapping
    public ResponseEntity<ProductReportDto> updateProductReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.productReportService.updateProductReport(updateReportDto));
    }

    @DeleteMapping("{productReportID}")
    public ResponseEntity<Void> deleteProductReport(@PathVariable("productReportID") UUID productReportID) {
        this.productReportService.deleteProductReport(productReportID);
        return ResponseEntity.noContent().build();
    }
}
