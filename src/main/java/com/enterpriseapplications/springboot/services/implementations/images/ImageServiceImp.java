package com.enterpriseapplications.springboot.services.implementations.images;


import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.images.ImageDao;
import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.services.interfaces.images.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService
{
    private final ImageDao imageDao;
    private final ModelMapper modelMapper;


    @Override
    public ImageDto getImage(Long imageID) {
        Image image = this.imageDao.findById(imageID).orElseThrow();
        ImageDto imageDto = new ImageDto();
        imageDto.setName(image.getName());
        imageDto.setType(image.getType());
        imageDto.setImage(ImageUtils.decompressImage(image.getImage()));
        return imageDto;
    }

    @Override
    public List<ImageDto> getImagesByName(String name) {
        List<Image> images = this.imageDao.getImagesByName(name);
        return images.stream().map(image -> this.modelMapper.map(image,ImageDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ImageDto> getImagesByType(String type) {
        List<Image> images = this.imageDao.getImagesByType(type);
        return images.stream().map(image -> this.modelMapper.map(image,ImageDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteImage(Long imageID) {
        this.imageDao.findById(imageID).orElseThrow();
        this.imageDao.deleteById(imageID);
    }
}
