package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.output.reports.MessageReportDto;
import com.enterpriseapplications.springboot.data.entities.reports.MessageReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageReportService
{
    Page<MessageReportDto> getMessageReports(Long messageID, Pageable pageable);
    void deleteMessageReport(Long messageID);
}