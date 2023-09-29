package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface MessageReportService
{
    MessageReportDto getReport(UUID reportID);
    PagedModel<MessageReportDto> getReports(Pageable pageable);
    PagedModel<MessageReportDto> getMessageReports(UUID messageID, Pageable pageable);
    MessageReportDto createMessageReport(CreateReportDto createReportDto,UUID messageID);
    MessageReportDto updateMessageReport(UpdateReportDto updateReportDto);
    void deleteMessageReport(UUID messageID);
}
