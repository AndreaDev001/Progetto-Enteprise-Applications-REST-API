package com.enterpriseapplications.springboot.services.implementations.images;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.util.ImageUtils;
import com.enterpriseapplications.springboot.data.dao.images.ImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.services.implementations.GenericServiceImp;
import com.enterpriseapplications.springboot.services.interfaces.images.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceImp extends GenericServiceImp<Image,ImageDto> implements ImageService
{
    private final ImageDao imageDao;


    public ImageServiceImp(ImageDao imageDao,ModelMapper modelMapper,PagedResourcesAssembler<Image> pagedResourcesAssembler) {
        super(modelMapper,Image.class,ImageDto.class,pagedResourcesAssembler);
        this.imageDao = imageDao;
    }

    @Override
    public PagedModel<ImageDto> getImages(Pageable pageable) {
        Page<Image> images = this.imageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(images,modelAssembler);
    }

    @Override
    public ImageDto getImage(UUID imageID) {
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
    public ImageOwner[] getImageOwners() {
        return ImageOwner.values();
    }

    @Override
    @Transactional
    public void deleteImage(UUID imageID) {
        this.imageDao.findById(imageID).orElseThrow();
        this.imageDao.deleteById(imageID);
    }
}
