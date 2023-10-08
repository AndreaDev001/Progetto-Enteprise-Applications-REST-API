package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReportServiceImpTest {

    private ReportServiceImp reportServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Report> pagedResourcesAssembler;
    private Report firstReport;
    private Report secondReport;

    @Mock
    private UserDao userDao;
    @Mock
    private ReportDao reportDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        this.reportServiceImp = new ReportServiceImp(reportDao,userDao,modelMapper,pagedResourcesAssembler);
        List<Report> reports = createReports();
        this.firstReport = reports.get(0);
        this.secondReport = reports.get(1);
    }

    static List<Report> createReports() {
        User reporter = User.builder().id(UUID.randomUUID()).build();
        User reported = User.builder().id(UUID.randomUUID()).build();
        Report firstReport = Report.builder().id(UUID.randomUUID()).description("description").reporter(reporter).reported(reported).reason(ReportReason.RACISM).type(ReportType.USER).build();
        Report secondReport = Report.builder().id(UUID.randomUUID()).description("description").reporter(reported).reported(reporter).reason(ReportReason.NUDITY).type(ReportType.USER).build();
        return List.of(firstReport,secondReport);
    }

    static boolean valid(Report report, ReportDto reportDto) {
        Assert.assertNotNull(reportDto);
        Assert.assertEquals(report.getId(),reportDto.getId());
        Assert.assertEquals(report.getDescription(),reportDto.getDescription());
        Assert.assertEquals(report.getReason(),reportDto.getReason());
        Assert.assertEquals(report.getType(),reportDto.getType());
        Assert.assertEquals(report.getReporter().getId(),reportDto.getReporter().getId());
        Assert.assertEquals(report.getReported().getId(),reportDto.getReported().getId());
        Assert.assertEquals(report.getCreatedDate(),reportDto.getCreatedDate());
        return true;
    }
    @Test
    void getReport() {
        given(this.reportDao.findById(firstReport.getId())).willReturn(Optional.of(firstReport));
        given(this.reportDao.findById(secondReport.getId())).willReturn(Optional.of(secondReport));
        ReportDto firstReport = this.reportServiceImp.getReport(this.firstReport.getId());
        ReportDto secondReport = this.reportServiceImp.getReport(this.secondReport.getId());
        Assert.assertTrue(valid(this.firstReport,firstReport));
        Assert.assertTrue(valid(this.secondReport,secondReport));
    }

    @Test
    void getReports() {
    }

    @Test
    void getCreatedReports() {
    }

    @Test
    void getReceivedReports() {
    }

    @Test
    void getReportsByReason() {
    }

    @Test
    void getReportsByType() {
    }

    @Test
    void getReportsBySpec() {
    }

    @Test
    void createReport() {
    }

    @Test
    void updateReport() {
    }
}