package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductReportServiceImp implements ProductReportService {

    private final ProductReportDao productReportDao;
    private final ModelMapper modelMapper;
}
