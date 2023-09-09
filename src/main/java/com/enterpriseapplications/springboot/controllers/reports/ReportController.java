package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.output.PaginationResponse;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("reason/{reason}")
    public ResponseEntity<PaginationResponse<ReportDto>> getReportsByReason(@PathVariable("reason") ReportReason reason, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReportDto> reports = this.reportService.getReportsByReason(reason, PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(reports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reports.getTotalPages(),reports.getTotalElements()));
    }

    @GetMapping("type/{type}")
    public ResponseEntity<PaginationResponse<ReportDto>> getReportsByType(@PathVariable("type") ReportType type,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReportDto> reports = this.reportService.getReportsByType(type,PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(reports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reports.getTotalPages(),reports.getTotalElements()));
    }

    @GetMapping("reporter/{reporterID}")
    public ResponseEntity<PaginationResponse<ReportDto>> getReportsByReporter(@PathVariable("reporterID") Long reporterID,@ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReportDto> reports = this.reportService.getCreatedReports(reporterID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(reports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reports.getTotalPages(),reports.getTotalElements()));
    }

    @GetMapping("reported/{reportedID}")
    public ResponseEntity<PaginationResponse<ReportDto>> getReportsByReported(@PathVariable("reportedID") Long reportedID, @ParameterObject @Valid PaginationRequest paginationRequest) {
        Page<ReportDto> reports = this.reportService.getReceivedReports(reportedID,PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(new PaginationResponse<>(reports.toList(),paginationRequest.getPage(),paginationRequest.getPageSize(),reports.getTotalPages(),reports.getTotalElements()));
    }

    @DeleteMapping("{reportID}")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportID") Long reportID) {
        this.reportService.deleteReport(reportID);
        return ResponseEntity.noContent().build();
    }
}
