package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
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
public class MessageReportServiceImp implements MessageReportService {

    private final MessageReportDao messageReportDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<MessageReportDto> getMessageReports(Long messageID, Pageable pageable) {
        Page<MessageReport> messageReports = this.messageReportDao.getMessageReports(messageID,pageable);
        return new PageImpl<>(messageReports.stream().map(messageReport -> this.modelMapper.map(MessageReport.class,MessageReportDto.class)).collect(Collectors.toList()),pageable,messageReports.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteMessageReport(Long messageID) {
        this.messageReportDao.findById(messageID);
        this.messageReportDao.deleteById(messageID);
    }
}
