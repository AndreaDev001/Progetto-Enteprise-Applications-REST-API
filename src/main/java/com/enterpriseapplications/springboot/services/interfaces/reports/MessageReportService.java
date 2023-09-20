package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageReportService
{
    Page<MessageReportDto> getMessageReports(Long messageID, Pageable pageable);
    MessageReportDto createMessageReport(CreateReportDto createReportDto,Long messageID);
    MessageReportDto updateMessageReport(UpdateReportDto updateReportDto);
    void deleteMessageReport(Long messageID);
}
