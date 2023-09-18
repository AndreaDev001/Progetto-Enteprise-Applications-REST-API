package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.input.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReportServiceImp implements ProductReportService {

    private final ProductReportDao productReportDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProductReportDto> getReports(Long productID, Pageable pageable) {
        Page<ProductReport> productReports = this.productReportDao.getProductReports(productID,pageable);
        return new PageImpl<>(productReports.stream().map(productReport -> this.modelMapper.map(ProductReport.class,ProductReportDto.class)).collect(Collectors.toList()),pageable,productReports.getTotalElements());
    }

    @Override
    @Transactional
    public ProductReportDto createProductReport(CreateReportDto createReportDto, Long productID) {
        User requiredUser = this.userDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        if(requiredProduct.getSeller().getId().equals(requiredUser.getId()))
            throw new InvalidFormat("errors.productReport.invalidReporter");
        ProductReport productReport = new ProductReport();
        productReport.setReporter(requiredUser);
        productReport.setReported(requiredProduct.getSeller());
        productReport.setProduct(requiredProduct);
        productReport.setDescription(createReportDto.getDescription());
        productReport.setReason(createReportDto.getReason());
        productReport.setType(ReportType.PRODUCT);
        this.productReportDao.save(productReport);
        return this.modelMapper.map(productReport,ProductReportDto.class);
    }

    @Override
    @Transactional
    public void deleteProductReport(Long productReportID) {
        this.productReportDao.findById(productReportID).orElseThrow();
        this.productReportDao.deleteById(productReportID);
    }
}
