package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateMessageDto;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import com.enterpriseapplications.springboot.services.TestUtils;
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

import static org.mockito.ArgumentMatchers.any;
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
        User sender = User.builder().id(UUID.randomUUID()).build();
        User receiver = User.builder().id(UUID.randomUUID()).build();
        Message firstMessage = Message.builder().id(UUID.randomUUID()).sender(sender).receiver(receiver).build();
        Message secondMessage = Message.builder().id(UUID.randomUUID()).sender(receiver).receiver(sender).build();
        TestUtils.generateValues(firstMessage);
        TestUtils.generateValues(secondMessage);
        TestUtils.generateValues(sender);
        TestUtils.generateValues(receiver);
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
        this.defaultBefore();
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
        User sender = User.builder().id(UUID.randomUUID()).build();
        User receiver = User.builder().id(UUID.randomUUID()).build();
        Message message = Message.builder().id(UUID.randomUUID()).sender(sender).receiver(receiver).build();
        CreateReportDto createReportDto = CreateReportDto.builder().reason(ReportReason.RACISM).description("description").build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.messageDao.findById(message.getId())).willReturn(Optional.of(message));
        given(this.messageReportDao.save(any(MessageReport.class))).willReturn(firstElement);
        MessageReportDto messageReportDto = this.messageReportServiceImp.createMessageReport(createReportDto,message.getId());
        Assert.assertTrue(valid(firstElement,messageReportDto));
    }

    @Test
    void getReportBetween() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        Message firstMessage = Message.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Message secondMessage = Message.builder().id(UUID.randomUUID()).build();
        given(this.messageReportDao.getMessageReport(firstMessage.getId(),firstUser.getId())).willReturn(Optional.of(firstElement));
        given(this.messageReportDao.getMessageReport(secondMessage.getId(),secondUser.getId())).willReturn(Optional.of(secondElement));
        MessageReportDto firstReport = this.messageReportServiceImp.getReport(firstUser.getId(),firstMessage.getId());
        MessageReportDto secondReport = this.messageReportServiceImp.getReport(secondUser.getId(),secondMessage.getId());
        Assert.assertTrue(valid(firstElement,firstReport));
        Assert.assertTrue(valid(secondElement,secondReport));
    }

    @Test
    void updateMessageReport() {
        MessageReport messageReport = new MessageReport();
        Message message = Message.builder().id(UUID.randomUUID()).build();
        messageReport.setId(UUID.randomUUID());
        messageReport.setMessage(message);
        UpdateReportDto updateReportDto = UpdateReportDto.builder().reportID(messageReport.getId()).description("description").build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.messageReportDao.findById(updateReportDto.getReportID())).willReturn(Optional.of(firstElement));
        given(this.messageReportDao.save(any(MessageReport.class))).willReturn(messageReport);
        MessageReportDto result = this.messageReportServiceImp.updateMessageReport(updateReportDto);
        Assert.assertTrue(valid(firstElement,result));
    }
}