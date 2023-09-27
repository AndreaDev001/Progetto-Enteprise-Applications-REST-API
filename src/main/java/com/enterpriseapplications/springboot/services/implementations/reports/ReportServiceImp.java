package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {

    private final ReportDao reportDao;
    private final UserDao userDao;
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
    public ReportDto createReport(CreateReportDto createReportDto,Long reportedID) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        User reported = this.userDao.findById(reportedID).orElseThrow();
        if(requiredUser.getId().equals(reportedID))
            throw new InvalidFormat("errors.report.invalidReporter");
        Report report = new Report();
        report.setReporter(requiredUser);
        report.setReported(reported);
        report.setDescription(createReportDto.getDescription());
        report.setReason(createReportDto.getReason());
        report.setType(ReportType.USER);
        this.reportDao.save(report);
        return this.modelMapper.map(report,ReportDto.class);
    }

    @Override
    @Transactional
    public ReportDto updateReport(UpdateReportDto updateReportDto) {
        Report requiredReport = this.reportDao.findById(updateReportDto.getReportID()).orElseThrow();
        if(updateReportDto.getDescription() != null)
            requiredReport.setDescription(updateReportDto.getDescription());
        if(updateReportDto.getReason() != null)
            requiredReport.setReason(updateReportDto.getReason());
        this.reportDao.save(requiredReport);
        return this.modelMapper.map(requiredReport,ReportDto.class);
    }

    @Override
    public ReportReason[] getReasons() {
        return ReportReason.values();
    }

    @Override
    public ReportType[] getTypes() {
        return ReportType.values();
    }

    @Override
    @Transactional
    public void deleteReport(Long reportID) {
        this.reportDao.findById(reportID).orElseThrow();
        this.reportDao.deleteById(reportID);
    }
}
