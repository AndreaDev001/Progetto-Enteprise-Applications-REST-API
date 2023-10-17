package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
import com.enterpriseapplications.springboot.services.interfaces.reports.ProductReportService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductReportServiceImp extends GenericServiceImp<ProductReport,ProductReportDto> implements ProductReportService {

    private final ProductReportDao productReportDao;
    private final UserDao userDao;
    private final ProductDao productDao;


    public ProductReportServiceImp(ProductReportDao productReportDao,UserDao userDao,ProductDao productDao,ModelMapper modelMapper,PagedResourcesAssembler<ProductReport> pagedResourcesAssembler) {
        super(modelMapper,ProductReport.class,ProductReportDto.class,pagedResourcesAssembler);
        this.productReportDao = productReportDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public ProductReportDto getReport(UUID reportID) {
        ProductReport productReport = this.productReportDao.findById(reportID).orElseThrow();
        return this.modelMapper.map(productReport,ProductReportDto.class);
    }

    @Override
    public PagedModel<ProductReportDto> getReports(Pageable pageable) {
        Page<ProductReport> productReports = this.productReportDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(productReports,modelAssembler);
    }

    @Override
    public PagedModel<ProductReportDto> getReports(UUID productID, Pageable pageable) {
        Page<ProductReport> productReports = this.productReportDao.getProductReports(productID,pageable);
        return this.pagedResourcesAssembler.toModel(productReports,modelAssembler);
    }

    @Override
    @Transactional
    public ProductReportDto createProductReport(CreateReportDto createReportDto, UUID productID) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(productID).orElseThrow();
        if(requiredProduct.getSeller().getId().equals(requiredUser.getId()))
            throw new InvalidFormat("errors.productReport.invalidReporter");
        ProductReport productReport = new ProductReport();
        productReport.setReporter(requiredUser);
        productReport.setReported(requiredProduct.getSeller());
        productReport.setProduct(requiredProduct);
        productReport.setDescription(createReportDto.getDescription());
        productReport.setReason(createReportDto.getReason());
        productReport.setType(ReportType.PRODUCT);
        return this.modelMapper.map(this.productReportDao.save(productReport),ProductReportDto.class);
    }

    @Override
    @Transactional
    public ProductReportDto updateProductReport(UpdateReportDto updateReportDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        ProductReport productReport = this.productReportDao.findById(updateReportDto.getReportID()).orElseThrow();
        if(requiredUser.getId().equals(productReport.getProduct().getSeller().getId()))
            throw new InvalidFormat("error.productReport.invalidUpdater");
        if(updateReportDto.getReason() != null)
            productReport.setReason(updateReportDto.getReason());
        if(updateReportDto.getDescription() != null)
            productReport.setDescription(updateReportDto.getDescription());
        this.productReportDao.save(productReport);
        return this.modelMapper.map(productReport,ProductReportDto.class);
    }

    @Override
    @Transactional
    public void deleteProductReport(UUID productReportID) {
        this.productReportDao.findById(productReportID).orElseThrow();
        this.productReportDao.deleteById(productReportID);
    }
}
