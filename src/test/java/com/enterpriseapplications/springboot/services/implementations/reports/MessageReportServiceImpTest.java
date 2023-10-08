package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
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
class MessageReportServiceImpTest {

    private MessageReportServiceImp messageReportServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<MessageReport> pagedResourcesAssembler;
    private MessageReport firstReport;
    private MessageReport secondReport;

    @Mock
    private MessageReportDao messageReportDao;
    @Mock
    private UserDao userDao;
    @Mock
    private MessageDao messageDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        messageReportServiceImp = new MessageReportServiceImp(messageReportDao,userDao,messageDao,modelMapper,pagedResourcesAssembler);
        Message firstMessage = Message.builder().id(UUID.randomUUID()).build();
        Message secondMessage = Message.builder().id(UUID.randomUUID()).build();
        List<Report> reports = ReportServiceImpTest.createReports();
        this.firstReport = new MessageReport(reports.get(0));
        this.secondReport = new MessageReport(reports.get(1));
        this.firstReport.setMessage(firstMessage);
        this.secondReport.setMessage(secondMessage);
    }

    boolean valid(MessageReport messageReport, MessageReportDto messageReportDto) {
        Assert.assertTrue(ReportServiceImpTest.valid(messageReport,messageReportDto));
        Assert.assertEquals(messageReport.getMessage().getId(),messageReportDto.getMessageID());
        Assert.assertEquals(messageReport.getMessage().getText(),messageReportDto.getMessageText());
        return true;
    }

    @Test
    void getReport() {
        given(this.messageReportDao.findById(this.firstReport.getId())).willReturn(Optional.of(firstReport));
        given(this.messageReportDao.findById(this.secondReport.getId())).willReturn(Optional.of(secondReport));
        MessageReportDto firstMessage = this.messageReportServiceImp.getReport(this.firstReport.getId());
        MessageReportDto secondMessage = this.messageReportServiceImp.getReport(this.secondReport.getId());
        Assert.assertTrue(valid(this.firstReport,firstMessage));
        Assert.assertTrue(valid(this.secondReport,secondMessage));
    }

    @Test
    void getReports() {
    }

    @Test
    void getMessageReports() {
    }

    @Test
    void createMessageReport() {
    }

    @Test
    void updateMessageReport() {
    }
}