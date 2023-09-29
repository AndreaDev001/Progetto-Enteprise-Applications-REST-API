package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.Message;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MessageReportServiceImp implements MessageReportService {

    private final MessageReportDao messageReportDao;
    private final UserDao userDao;
    private final MessageDao messageDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<MessageReport,MessageReportDto> modelAssembler;
    private final PagedResourcesAssembler<MessageReport> pagedResourcesAssembler;


    public MessageReportServiceImp(MessageReportDao messageReportDao,UserDao userDao,MessageDao messageDao,ModelMapper modelMapper,PagedResourcesAssembler<MessageReport> pagedResourcesAssembler) {
        this.messageReportDao = messageReportDao;
        this.userDao = userDao;
        this.messageDao = messageDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(MessageReport.class,MessageReportDto.class,modelMapper);
    }

    @Override
    public MessageReportDto getReport(UUID reportID) {
        MessageReport messageReport = this.messageReportDao.findById(reportID).orElseThrow();
        return this.modelMapper.map(messageReport,MessageReportDto.class);
    }

    @Override
    public PagedModel<MessageReportDto> getReports(Pageable pageable) {
        Page<MessageReport> reports = this.messageReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(reports,modelAssembler);
    }

    @Override
    public PagedModel<MessageReportDto> getMessageReports(UUID messageID, Pageable pageable) {
        Page<MessageReport> messageReports = this.messageReportDao.getMessageReports(messageID,pageable);
        return this.pagedResourcesAssembler.toModel(messageReports,modelAssembler);
    }

    @Override
    @Transactional
    public MessageReportDto createMessageReport(CreateReportDto createReportDto, UUID messageID) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Message requiredMessage = this.messageDao.findById(messageID).orElseThrow();
        if(requiredMessage.getSender().getId().equals(requiredUser.getId()))
            throw new InvalidFormat(("errors.messageReport.invalidReporter"));
        MessageReport messageReport = new MessageReport();
        messageReport.setReporter(requiredUser);
        messageReport.setReported(requiredMessage.getSender());
        messageReport.setMessage(requiredMessage);
        messageReport.setDescription(createReportDto.getDescription());
        messageReport.setReason(createReportDto.getReason());
        messageReport.setType(ReportType.MESSAGE);
        this.messageReportDao.save(messageReport);
        return this.modelMapper.map(messageReport,MessageReportDto.class);
    }

    @Override
    @Transactional
    public MessageReportDto updateMessageReport(UpdateReportDto updateReportDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        MessageReport messageReport = this.messageReportDao.findById(updateReportDto.getReportID()).orElseThrow();
        if(messageReport.getMessage().getSender().getId().equals(requiredUser.getId()))
            throw new InvalidFormat("errors.messageReport.invalidUpdater");
        if(updateReportDto.getDescription() != null)
            messageReport.setDescription(updateReportDto.getDescription());
        if(updateReportDto.getReason() != null)
            messageReport.setReason(updateReportDto.getReason());
        this.messageReportDao.save(messageReport);
        return this.modelMapper.map(messageReport,MessageReportDto.class);

    }

    @Override
    @Transactional
    public void deleteMessageReport(UUID messageID) {
        this.messageReportDao.findById(messageID);
        this.messageReportDao.deleteById(messageID);
    }
}
