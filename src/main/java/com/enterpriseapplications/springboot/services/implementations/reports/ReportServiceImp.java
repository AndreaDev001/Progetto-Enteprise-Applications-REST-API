package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {

    private final ReportDao reportDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<ReportDto> getCreatedReports(Long userID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getCreatedReports(userID,pageable);
        return new PageImpl<>(reports.stream().map(report -> this.modelMapper.map(report, ReportDto.class)).collect(Collectors.toList()),pageable,reports.getTotalElements());
    }

    @Override
    public Page<ReportDto> getReceivedReports(Long userID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReceivedReports(userID,pageable);
        return new PageImpl<>(reports.stream().map(report -> this.modelMapper.map(report,ReportDto.class)).collect(Collectors.toList()),pageable,reports.getTotalElements());
    }

    @Override
    public Page<ReportDto> getReportsByReason(ReportReason reason, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByReason(reason,pageable);
        return new PageImpl<>(reports.stream().map(report -> this.modelMapper.map(report,ReportDto.class)).collect(Collectors.toList()),pageable,reports.getTotalElements());
    }

    @Override
    public Page<ReportDto> getReportsByType(ReportType type, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByType(type,pageable);
        return new PageImpl<>(reports.stream().map(report -> this.modelMapper.map(report,ReportDto.class)).collect(Collectors.toList()),pageable,reports.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteReport(Long reportID) {
        this.reportDao.findById(reportID).orElseThrow();
        this.reportDao.deleteById(reportID);
    }
}
