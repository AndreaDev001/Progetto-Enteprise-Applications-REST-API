package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import com.enterpriseapplications.springboot.services.TestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductReportServiceImpTest extends GenericTestImp<ProductReport,ProductReportDto> {

    private ProductReportServiceImp productReportServiceImp;
    @Mock
    private ProductReportDao productReportDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;


    @Override
    protected void init() {
        super.init();
        productReportServiceImp = new ProductReportServiceImp(productReportDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        List<Report> reports = ReportServiceImpTest.createReports();
        User firstSeller = User.builder().id(UUID.randomUUID()).build();
        User secondSeller = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).seller(firstSeller).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).seller(secondSeller).build();
        TestUtils.generateValues(firstProduct);
        TestUtils.generateValues(secondProduct);
        TestUtils.generateValues(firstSeller);
        TestUtils.generateValues(secondSeller);
        this.firstElement = new ProductReport(reports.get(0));
        this.secondElement = new ProductReport(reports.get(1));
        this.firstElement.setProduct(firstProduct);
        this.secondElement.setProduct(secondProduct);
        this.elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }


    public boolean valid(ProductReport productReport, ProductReportDto productReportDto) {
        Assert.assertTrue(ReportServiceImpTest.baseValid(productReport,productReportDto));
        Assert.assertEquals(productReport.getProduct().getId(),productReportDto.getProduct().getId());
        return true;
    }

    @Test
    void getReport() {
        given(this.productReportDao.findById(this.firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.productReportDao.findById(this.secondElement.getId())).willReturn(Optional.of(secondElement));
        ProductReportDto firstReport = this.productReportServiceImp.getReport(this.firstElement.getId());
        ProductReportDto secondReport = this.productReportServiceImp.getReport(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstReport));
        Assert.assertTrue(valid(this.secondElement,secondReport));
    }

    @Test
    void getReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productReportDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductReportDto> productReports = this.productReportServiceImp.getReports(pageRequest);
        Assert.assertTrue(compare(elements,productReports.getContent().stream().toList()));
        Assert.assertTrue(validPage(productReports,20,0,1,2));
    }

    @Test
    void getProductReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productReportDao.getProductReports(product.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductReportDto> productReports = this.productReportServiceImp.getReports(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,productReports.getContent().stream().toList()));
        Assert.assertTrue(validPage(productReports,20,0,1,2));
    }

    @Test
    void createReport() {
        User user = User.builder().id(UUID.randomUUID()).build();
        Product product = Product.builder().id(UUID.randomUUID()).seller(user).build();
        CreateReportDto createReportDto = CreateReportDto.builder().reason(ReportReason.RACISM).description("description").build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.productDao.findById(product.getId())).willReturn(Optional.of(product));
        given(this.productReportDao.save(any(ProductReport.class))).willReturn(firstElement);
        ProductReportDto productReportDto = this.productReportServiceImp.createProductReport(createReportDto,product.getId());
        Assert.assertTrue(valid(firstElement,productReportDto));
    }

    @Test
    void getProductReportBetween() {
        User firstUser = User.builder().id(UUID.randomUUID()).build();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        User secondUser = User.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        given(this.productReportDao.getProductReport(firstProduct.getId(),firstUser.getId())).willReturn(Optional.of(firstElement));
        given(this.productReportDao.getProductReport(secondProduct.getId(),secondUser.getId())).willReturn(Optional.of(secondElement));
        ProductReportDto firstReport = this.productReportServiceImp.getReport(firstUser.getId(),firstProduct.getId());
        ProductReportDto secondReport = this.productReportServiceImp.getReport(secondUser.getId(),secondProduct.getId());
        Assert.assertTrue(valid(firstElement,firstReport));
        Assert.assertTrue(valid(secondElement,secondReport));
    }


    @Test
    void updateProductReport() {
        Product product = Product.builder().id(UUID.randomUUID()).build();
        ProductReport productReport = new ProductReport();
        productReport.setId(UUID.randomUUID());
        productReport.setProduct(product);
        UpdateReportDto updateReportDto = UpdateReportDto.builder().reportID(productReport.getId()).description("description").reason(ReportReason.RACISM).build();
        given(this.userDao.findById(authenticatedUser.getId())).willReturn(Optional.of(authenticatedUser));
        given(this.productReportDao.findById(updateReportDto.getReportID())).willReturn(Optional.of(firstElement));
        given(this.productReportDao.save(any(ProductReport.class))).willReturn(firstElement);
        ProductReportDto result = this.productReportServiceImp.updateProductReport(updateReportDto);
        Assert.assertTrue(valid(firstElement,result));
    }
}