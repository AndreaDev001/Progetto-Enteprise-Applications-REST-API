package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.ReportSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;


    @GetMapping
    @PreAuthorize("@permissionHandler.hasRole('ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReports(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("{reportID}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("reportID") UUID reportID) {
        return ResponseEntity.ok(this.reportService.getReport(reportID));
    }

    @GetMapping("reason/{reason}")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReason(@PathVariable("reason") ReportReason reason, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByReason(reason, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/spec")
    public ResponseEntity<PagedModel<ReportDto>> getReportsBySpec(@ParameterObject @Valid ReportSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsBySpec(ReportSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("type/{type}")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByType(@PathVariable("type") ReportType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByType(type,PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("reporter/{reporterID}")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReporter(@PathVariable("reporterID") UUID reporterID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getCreatedReports(reporterID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("reported/{reportedID}")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReported(@PathVariable("reportedID") UUID reportedID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReceivedReports(reportedID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/orderTypes")
    public ResponseEntity<ReportSpecifications.OrderType[]> getOrderTypes() {
        return ResponseEntity.ok(this.reportService.getOrderTypes());
    }
    
    @GetMapping("reasons")
    public ResponseEntity<ReportReason[]> getReasons() {
        return ResponseEntity.ok(this.reportService.getReasons());
    }

    @GetMapping("types")
    public ResponseEntity<ReportType[]> getTypes() {
        return ResponseEntity.ok(this.reportService.getTypes());
    }

    @PostMapping("/{userID}")
    public ResponseEntity<ReportDto> createReport(@RequestBody @Valid CreateReportDto createReportDto, @PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.reportService.createReport(createReportDto,userID));
    }

    @PutMapping
    public ResponseEntity<ReportDto> updateReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.reportService.updateReport(updateReportDto));
    }

    @DeleteMapping("{reportID}")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportID") UUID reportID) {
        this.reportService.deleteReport(reportID);
        return ResponseEntity.noContent().build();
    }
}
