package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.ImageDao;
import com.enterpriseapplications.springboot.data.dto.output.ImageDto;
import com.enterpriseapplications.springboot.data.entities.Image;
import com.enterpriseapplications.springboot.services.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ImageDto getImage(String name) {
        Image image = this.imageDao.getImage(name).orElseThrow();
        ImageDto imageDto = new ImageDto();
        imageDto.setName(image.getName());
        imageDto.setType(image.getType());
        imageDto.setImage(ImageUtils.decompressImage(image.getImage()));
        return imageDto;
    }

    @Override
    public List<ImageDto> getImagesByType(String type) {
        List<Image> images = this.imageDao.getImagesByType(type);
        return images.stream().map(image -> this.modelMapper.map(image,ImageDto.class)).collect(Collectors.toList());
    }


    @Override
    public ImageDto uploadUserImage(Long userID, MultipartFile file) {
        return this.uploadImage(file);
    }

    @Override
    public List<ImageDto> uploadProductImages(Long productID, List<MultipartFile> files) {
        List<ImageDto> results = new ArrayList<>();
        files.forEach((MultipartFile file) -> {
            ImageDto imageDto = this.uploadImage(file);
            results.add(imageDto);
        });
        return results;
    }

    @SneakyThrows
    private ImageDto uploadImage(MultipartFile file) {
        Image image = new Image();
        image.setImage(ImageUtils.compressImage(file.getBytes()));
        image.setType(file.getContentType());
        image.setName(file.getOriginalFilename());
        imageDao.save(image);

        ImageDto imageDto = this.modelMapper.map(image,ImageDto.class);
        imageDto.addLinks();
        return imageDto;
    }

    @Override
    @Transactional
    public void deleteImage(Long imageID) {
        this.imageDao.findById(imageID).orElseThrow();
        this.imageDao.deleteById(imageID);
    }
}
