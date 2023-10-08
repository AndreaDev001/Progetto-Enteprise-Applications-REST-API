package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.images.ProductImageDao;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import com.enterpriseapplications.springboot.services.interfaces.images.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductImageServiceImp implements ProductImageService
{
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<ProductImage,ProductImageDto> modelAssembler;
    private final PagedResourcesAssembler<ProductImage> pagedResourcesAssembler;

    public ProductImageServiceImp(ProductDao productDao,ProductImageDao productImageDao,ModelMapper modelMapper,PagedResourcesAssembler<ProductImage> pagedResourcesAssembler) {
        this.productDao = productDao;
        this.productImageDao = productImageDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(ProductImage.class,ProductImageDto.class,modelMapper);
    }

    @Override
    public ProductImageDto getProductImage(UUID id) {
        return this.modelMapper.map(this.productImageDao.findById(id).orElseThrow(),ProductImageDto.class);
    }

    @Override
    public PagedModel<ProductImageDto> getImages(Pageable pageable) {
        Page<ProductImage> productImages = this.productImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(productImages,modelAssembler);
    }

    @Override
    public List<ProductImageDto> getProductImages(UUID productID) {
        List<ProductImage> productImages = this.productImageDao.getProductImages(productID);
        return productImages.stream().map(productImage -> {
            ProductImageDto productImageDto = this.modelMapper.map(productImage,ProductImageDto.class);
            productImageDto.addLinks();
            return productImageDto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @SneakyThrows
    public List<ProductImageDto> uploadImages(CreateProductImageDto createProductImageDto) {
        List<ProductImageDto> results = new ArrayList<>();
        Product requiredProduct = this.productDao.findById(createProductImageDto.getProductID()).orElseThrow();
        for(MultipartFile multipartFile : createProductImageDto.getFiles()) {
            ProductImage productImage = new ProductImage(requiredProduct,multipartFile);
            ProductImageDto productImageDto = this.modelMapper.map(productImage,ProductImageDto.class);
            productImageDto.addLinks();
            this.productImageDao.save(productImage);
            results.add(productImageDto);
        }
        return results;
    }

    @Override
    public ProductImageDto getFirstProductImage(UUID productID) {
        return this.getProductImage(productID,0);
    }

    @Override
    public ProductImageDto getLastProductImage(UUID productID) {
        List<ProductImage> productImages = this.productImageDao.getProductImages(productID);
        return this.getProductImage(productID,productImages.size() - 1);
    }

    @Override
    public ProductImageDto getProductImage(UUID productID, Integer index) {
        List<ProductImage> productImages = this.productImageDao.getProductImages(productID);
        if(productImages.isEmpty())
            throw new InvalidFormat("error.productImage.emptySet");
        if(productImages.size() < index)
            throw new InvalidFormat("error.productImage.invalidSize");
        ProductImage requiredImage = productImages.get(index);
        ProductImageDto productImageDto = this.modelMapper.map(requiredImage,ProductImageDto.class);
        productImageDto.setImage(ImageUtils.decompressImage(requiredImage.getImage()));
        return productImageDto;
    }

    @Override
    @Transactional
    public void deleteProductImages(UUID productID) {
        Product requiredProduct = this.productDao.findById(productID).orElseThrow();
        List<ProductImage> productImages = this.productImageDao.getProductImages(productID);
        for(ProductImage productImage : productImages)
            this.productImageDao.deleteById(productImage.getId());
    }
}
