package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.ReportDao;
import com.enterpriseapplications.springboot.services.interfaces.reports.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {
    private final ReportDao reportDao;
    private final ModelMapper modelMapper;
}
