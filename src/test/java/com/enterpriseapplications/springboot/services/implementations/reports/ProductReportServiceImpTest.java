package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ReportDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductReportServiceImpTest {

    private ProductReportServiceImp productReportServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<ProductReport> pagedResourcesAssembler;
    private ProductReport firstReport;
    private ProductReport secondReport;

    @Mock
    private ProductReportDao productReportDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;


    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        productReportServiceImp = new ProductReportServiceImp(productReportDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        List<Report> reports = ReportServiceImpTest.createReports();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        this.firstReport = new ProductReport(reports.get(0));
        this.secondReport = new ProductReport(reports.get(1));
        this.firstReport.setProduct(firstProduct);
        this.secondReport.setProduct(secondProduct);

    }


    boolean valid(ProductReport productReport, ProductReportDto productReportDto) {
        Assert.assertTrue(ReportServiceImpTest.valid(productReport,productReportDto));
        Assert.assertEquals(productReport.getProduct().getId(),productReportDto.getProduct().getId());
        return true;
    }

    @Test
    void getReport() {
        given(this.productReportDao.findById(this.firstReport.getId())).willReturn(Optional.of(firstReport));
        given(this.productReportDao.findById(this.secondReport.getId())).willReturn(Optional.of(secondReport));
        ProductReportDto firstReport = this.productReportServiceImp.getReport(this.firstReport.getId());
        ProductReportDto secondReport = this.productReportServiceImp.getReport(this.secondReport.getId());
        Assert.assertTrue(valid(this.firstReport,firstReport));
        Assert.assertTrue(valid(this.secondReport,secondReport));
    }

    @Test
    void getReports() {
    }

    @Test
    void testGetReports() {
    }

    @Test
    void createProductReport() {
    }

    @Test
    void updateProductReport() {
    }
}