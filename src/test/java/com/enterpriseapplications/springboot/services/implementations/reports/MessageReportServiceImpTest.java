package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
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
class MessageReportServiceImpTest extends GenericTestImp<MessageReport,MessageReportDto> {

    private MessageReportServiceImp messageReportServiceImp;
    @Mock
    private MessageReportDao messageReportDao;
    @Mock
    private UserDao userDao;
    @Mock
    private MessageDao messageDao;


    @Override
    protected void init() {
        super.init();
        messageReportServiceImp = new MessageReportServiceImp(messageReportDao,userDao,messageDao,modelMapper,pagedResourcesAssembler);
        Message firstMessage = Message.builder().id(UUID.randomUUID()).build();
        Message secondMessage = Message.builder().id(UUID.randomUUID()).build();
        List<Report> reports = ReportServiceImpTest.createReports();
        this.firstElement = new MessageReport(reports.get(0));
        this.secondElement = new MessageReport(reports.get(1));
        this.firstElement.setMessage(firstMessage);
        this.secondElement.setMessage(secondMessage);
        this.elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }

    public boolean valid(MessageReport messageReport, MessageReportDto messageReportDto) {
        Assert.assertTrue(ReportServiceImpTest.baseValid(messageReport,messageReportDto));
        Assert.assertEquals(messageReport.getMessage().getId(),messageReportDto.getMessageID());
        Assert.assertEquals(messageReport.getMessage().getText(),messageReportDto.getMessageText());
        return true;
    }

    @Test
    void getReport() {
        given(this.messageReportDao.findById(this.firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.messageReportDao.findById(this.secondElement.getId())).willReturn(Optional.of(secondElement));
        MessageReportDto firstMessage = this.messageReportServiceImp.getReport(this.firstElement.getId());
        MessageReportDto secondMessage = this.messageReportServiceImp.getReport(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstMessage));
        Assert.assertTrue(valid(this.secondElement,secondMessage));
    }

    @Test
    void getReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.messageReportDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageReportDto> messageReports = this.messageReportServiceImp.getReports(pageRequest);
        Assert.assertTrue(compare(this.elements,messageReports.getContent().stream().toList()));
        Assert.assertTrue(validPage(messageReports,20,0,1,2));
    }

    @Test
    void getMessageReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        Message message = Message.builder().id(UUID.randomUUID()).build();
        given(this.messageReportDao.getMessageReports(message.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<MessageReportDto> messageReports = this.messageReportServiceImp.getMessageReports(message.getId(),pageRequest);
        Assert.assertTrue(compare(this.elements,messageReports.getContent().stream().toList()));
        Assert.assertTrue(validPage(messageReports,20,0,1,2));
    }

    @Test
    void createMessageReport() {
    }

    @Test
    void updateMessageReport() {
    }
}