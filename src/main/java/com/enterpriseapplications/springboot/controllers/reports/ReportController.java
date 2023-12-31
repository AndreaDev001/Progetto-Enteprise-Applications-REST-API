package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.data.dao.specifications.ProductSpecifications;
import com.enterpriseapplications.springboot.data.dao.specifications.ReportSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ReportController {
    private final ReportService reportService;


    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReports(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReports(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/report/{reporterID}/{reportedID}/{reportType}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<ReportDto> getReportBetween(@PathVariable("reporterID") UUID reporterID,@PathVariable("reportedID") UUID reportedID,@PathVariable("reportType") ReportType reportType) {
        ReportDto reportDto = this.reportService.getReportBetween(reporterID,reportedID,reportType);
        return ResponseEntity.ok(reportDto);
    }

    @GetMapping("/private/{reportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#reportID)")
    public ResponseEntity<ReportDto> getReport(@PathVariable("reportID") UUID reportID) {
        return ResponseEntity.ok(this.reportService.getReport(reportID));
    }

    @GetMapping("/private/reason/{reason}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReason(@PathVariable("reason") ReportReason reason, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByReason(reason, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/spec")
    @Cacheable(value = CacheConfig.CACHE_SEARCH_REPORTS,key = "{#filter.toString(),#paginationRequest.toString()}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsBySpec(@ParameterObject @Valid ReportSpecifications.Filter filter,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsBySpec(ReportSpecifications.withFilter(filter),PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/{reportID}/similar")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getSimilarReports(@PathVariable("reportID") UUID reportID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getSimilarReports(reportID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByType(@PathVariable("type") ReportType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReportsByType(type,PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/reporter/{reporterID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReporter(@PathVariable("reporterID") UUID reporterID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getCreatedReports(reporterID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/private/reported/{reportedID}")
    @PreAuthorize("@permissionHandler.hasAccess(#reporterID)")
    public ResponseEntity<PagedModel<ReportDto>> getReportsByReported(@PathVariable("reportedID") UUID reportedID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ReportDto> reports = this.reportService.getReceivedReports(reportedID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/public/orderTypes")
    public ResponseEntity<ReportSpecifications.OrderType[]> getOrderTypes() {
        return ResponseEntity.ok(this.reportService.getOrderTypes());
    }
    
    @GetMapping("/public/reasons")
    public ResponseEntity<ReportReason[]> getReasons() {
        return ResponseEntity.ok(this.reportService.getReasons());
    }

    @GetMapping("/public/types")
    public ResponseEntity<ReportType[]> getTypes() {
        return ResponseEntity.ok(this.reportService.getTypes());
    }

    @PostMapping("/private/{userID}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_USER')")
    public ResponseEntity<ReportDto> createReport(@RequestBody @Valid CreateReportDto createReportDto, @PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.reportService.createReport(createReportDto,userID));
    }

    @PutMapping("/private")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#updateReportDto.reportID)")
    public ResponseEntity<ReportDto> updateReport(@RequestBody @Valid UpdateReportDto updateReportDto) {
        return ResponseEntity.ok(this.reportService.updateReport(updateReportDto));
    }

    @DeleteMapping("/private/{reportID}")
    @PreAuthorize("@permissionHandler.hasAccess(@reportDao,#reportID)")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportID") UUID reportID) {
        this.reportService.deleteReport(reportID);
        return ResponseEntity.noContent().build();
    }
}
