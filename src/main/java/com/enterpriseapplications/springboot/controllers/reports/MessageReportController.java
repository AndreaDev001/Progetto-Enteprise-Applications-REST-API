package com.enterpriseapplications.springboot.controllers.reports;


import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messageReports")
@RequiredArgsConstructor
public class MessageReportController {
    private final MessageReportService messageReportService;
}
