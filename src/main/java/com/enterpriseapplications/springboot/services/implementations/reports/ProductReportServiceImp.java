package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
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
public class ProductReportServiceImp implements ProductReportService {

    private final ProductReportDao productReportDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductReportDto> getReports(Long productID, Pageable pageable) {
        Page<ProductReport> productReports = this.productReportDao.getProductReports(productID,pageable);
        return new PageImpl<>(productReports.stream().map(productReport -> this.modelMapper.map(ProductReport.class,ProductReportDto.class)).collect(Collectors.toList()),pageable,productReports.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteProductReport(Long productReportID) {
        this.productReportDao.findById(productReportID).orElseThrow();
        this.productReportDao.deleteById(productReportID);
    }
}
