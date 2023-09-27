package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import com.enterpriseapplications.springboot.services.interfaces.ProductService;
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
public class ProductServiceImp implements ProductService
{
    private final ProductDao productDao;
    private final ModelMapper modelMapper;


    @Override
    public Page<ProductDto> getProductsBySeller(Long sellerID, Pageable pageable) {
        Page<Product> products = this.productDao.getProductsBySeller(sellerID,pageable);
        return new PageImpl<>(products.stream().map(product -> this.modelMapper.map(product,ProductDto.class)).collect(Collectors.toList()),pageable,products.getTotalElements());
    }

    @Override
    public ProductDto getProductDetails(Long productID) {
        Product product = this.productDao.findById(productID).orElseThrow();
        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UpdateProductDto updateProductDto) {
        Product requiredProduct =  this.productDao.findById(updateProductDto.getProductID()).orElseThrow();
        if(updateProductDto.getDescription() != null)
            requiredProduct.setDescription(updateProductDto.getDescription());
        if(updateProductDto.getBrand() != null)
            requiredProduct.setBrand(updateProductDto.getBrand());
        if(updateProductDto.getCondition() != null)
            requiredProduct.setCondition(updateProductDto.getCondition());
        if(updateProductDto.getVisibility() != null)
            requiredProduct.setVisibility(updateProductDto.getVisibility());
        this.productDao.save(requiredProduct);
        return this.modelMapper.map(requiredProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productID) {
        this.productDao.findById(productID);
        this.productDao.deleteById(productID);
    }

    @Override
    public ProductCondition[] getConditions() {
        return ProductCondition.values();
    }

    @Override
    public ProductVisibility[] getVisibilities() {
        return ProductVisibility.values();
    }
}
