package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ReportServiceImpTest extends GenericTestImp<Report,ReportDto> {

    private ReportServiceImp reportServiceImp;

    @Mock
    private UserDao userDao;
    @Mock
    private ReportDao reportDao;


    @Override
    public void init() {
        super.init();
        this.reportServiceImp = new ReportServiceImp(reportDao,userDao,modelMapper,pagedResourcesAssembler);
        List<Report> reports = createReports();
        this.firstElement = reports.get(0);
        this.secondElement = reports.get(1);
        elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    static List<Report> createReports() {
        User reporter = User.builder().id(UUID.randomUUID()).build();
        User reported = User.builder().id(UUID.randomUUID()).build();
        Report firstReport = Report.builder().id(UUID.randomUUID()).description("description").reporter(reporter).reported(reported).reason(ReportReason.RACISM).type(ReportType.USER).build();
        Report secondReport = Report.builder().id(UUID.randomUUID()).description("description").reporter(reported).reported(reporter).reason(ReportReason.NUDITY).type(ReportType.USER).build();
        return List.of(firstReport,secondReport);
    }
    static boolean baseValid(Report report, ReportDto reportDto) {
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

    @Override
    protected boolean valid(Report entity, ReportDto dto) {
        return baseValid(entity,dto);
    }

    @Test
    void getReport() {
        given(this.reportDao.findById(firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.reportDao.findById(secondElement.getId())).willReturn(Optional.of(secondElement));
        ReportDto firstReport = this.reportServiceImp.getReport(this.firstElement.getId());
        ReportDto secondReport = this.reportServiceImp.getReport(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstReport));
        Assert.assertTrue(valid(this.secondElement,secondReport));
    }

    @Test
    void getReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reportDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReportDto> reports = this.reportServiceImp.getReports(pageRequest);
        Assert.assertTrue(compare(elements,reports.getContent().stream().toList()));
    }

    @Test
    void getCreatedReports() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reportDao.getCreatedReports(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReportDto> reports =  this.reportServiceImp.getCreatedReports(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,reports.getContent().stream().toList()));
    }

    @Test
    void getReceivedReports() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reportDao.getReceivedReports(user.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReportDto> reports = this.reportServiceImp.getReceivedReports(user.getId(),pageRequest);
        Assert.assertTrue(compare(elements,reports.getContent().stream().toList()));
    }

    @Test
    void getReportsByReason() {
        User user = User.builder().id(UUID.randomUUID()).build();
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reportDao.getReportsByReason(ReportReason.RACISM,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReportDto> reports = this.reportServiceImp.getReportsByReason(ReportReason.RACISM,pageRequest);
        Assert.assertTrue(compare(elements,reports.getContent().stream().toList()));
    }

    @Test
    void getReportsByType() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.reportDao.getReportsByType(ReportType.USER,pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ReportDto> reports = this.reportServiceImp.getReportsByType(ReportType.USER,pageRequest);
        Assert.assertTrue(compare(elements,reports.getContent().stream().toList()));
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