package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.data.dao.specifications.ReportSpecifications;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class ReportServiceImp extends GenericServiceImp<Report,ReportDto> implements ReportService {

    private final ReportDao reportDao;
    private final UserDao userDao;


    public ReportServiceImp(ReportDao reportDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Report> pagedResourcesAssembler) {
        super(modelMapper,Report.class,ReportDto.class,pagedResourcesAssembler);
        this.reportDao = reportDao;
        this.userDao = userDao;
    }

    @Override
    public ReportDto getReport(UUID reportID) {
        Report report = this.reportDao.findById(reportID).orElseThrow();
        return this.modelMapper.map(report,ReportDto.class);
    }

    @Override
    public ReportSpecifications.OrderType[] getOrderTypes() {
        return ReportSpecifications.OrderType.values();
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_ALL_REPORTS)
    public PagedModel<ReportDto> getReports(Pageable pageable) {
        Page<Report> reports = this.reportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
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
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_REPORTS,CacheConfig.CACHE_ALL_REPORTS},allEntries = true)
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
        return this.modelMapper.map(this.reportDao.save(report),ReportDto.class);
    }

    @Override
    @CacheEvict(value = {CacheConfig.CACHE_ALL_REPORTS,CacheConfig.CACHE_SEARCH_REPORTS},allEntries = true)
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
    @CacheEvict(value = {CacheConfig.CACHE_SEARCH_REPORTS,CacheConfig.CACHE_ALL_REPORTS},allEntries = true)
    public void deleteReport(UUID reportID) {
        this.reportDao.findById(reportID).orElseThrow();
        this.reportDao.deleteById(reportID);
    }
}
