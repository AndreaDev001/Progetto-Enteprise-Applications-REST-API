package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.GenericModelAssembler;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ReportServiceImp implements ReportService {

    private final ReportDao reportDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Report,ReportDto> modelAssembler;
    private final PagedResourcesAssembler<Report> pagedResourcesAssembler;


    public ReportServiceImp(ReportDao reportDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Report> pagedResourcesAssembler) {
        this.reportDao = reportDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Report.class,ReportDto.class,modelMapper);
    }

    @Override
    public PagedModel<ReportDto> getCreatedReports(UUID userID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getCreatedReports(userID,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReceivedReports(UUID userID, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReceivedReports(userID,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByReason(ReportReason reason, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByReason(reason,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsByType(ReportType type, Pageable pageable) {
        Page<Report> reports = this.reportDao.getReportsByType(type,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<ReportDto> getReportsBySpec(Specification<Report> specification, Pageable pageable) {
        Page<Report> reports = this.reportDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    @Transactional
    public ReportDto createReport(CreateReportDto createReportDto,UUID reportedID) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
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
    public void deleteReport(UUID reportID) {
        this.reportDao.findById(reportID).orElseThrow();
        this.reportDao.deleteById(reportID);
    }
}
