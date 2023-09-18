package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.input.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    Page<ReportDto> getCreatedReports(Long userID, Pageable pageable);
    Page<ReportDto> getReceivedReports(Long userID,Pageable pageable);
    Page<ReportDto> getReportsByReason(ReportReason reason, Pageable pageable);
    Page<ReportDto> getReportsByType(ReportType type,Pageable pageable);
    ReportDto createReport(CreateReportDto createReportDto,Long reportedID);
    void deleteReport(Long reportID);
}
