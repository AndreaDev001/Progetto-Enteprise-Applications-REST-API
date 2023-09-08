package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.MessageReportDao;
import com.enterpriseapplications.springboot.services.interfaces.reports.MessageReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageReportServiceImp implements MessageReportService {

    private final MessageReportDao messageReportDao;
    private final ModelMapper modelMapper;
}
