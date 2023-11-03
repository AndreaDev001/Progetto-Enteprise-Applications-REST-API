package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.ProductImageDao;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.images.ProductImage;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
import com.enterpriseapplications.springboot.services.interfaces.images.ProductImageService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductImageServiceImp extends GenericServiceImp<ProductImage,ProductImageDto> implements ProductImageService
{
    private final ProductDao productDao;
    private final UserDao userDao;
    private final ProductImageDao productImageDao;

    public ProductImageServiceImp(UserDao userDao,ProductDao productDao,ProductImageDao productImageDao,ModelMapper modelMapper,PagedResourcesAssembler<ProductImage> pagedResourcesAssembler) {
        super(modelMapper,ProductImage.class,ProductImageDto.class,pagedResourcesAssembler);
        this.userDao = userDao;
        this.productDao = productDao;
        this.productImageDao = productImageDao;
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
    public List<ProductImageDto> uploadImages(UUID productID,List<MultipartFile> files) {
        List<ProductImageDto> results = new ArrayList<>();
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Product requiredProduct = this.productDao.findById(productID).orElseThrow();
        for(MultipartFile multipartFile : files) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(requiredProduct);
            productImage.setImage(multipartFile.getBytes());
            productImage.setType(ImageUtils.getImageType(multipartFile.getContentType()));
            productImage = this.productImageDao.save(productImage);
            ProductImageDto productImageDto = this.modelMapper.map(productImage,ProductImageDto.class);
            productImageDto.addLinks();
            results.add(productImageDto);
        }
        return results;
    }

    @Override
    public Integer getAmount(UUID productID) {
        return this.productImageDao.getProductImages(productID).size();
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
        productImageDto.setImage(requiredImage.getImage());
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
