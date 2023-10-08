package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.images.ImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.images.Image;
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
class ImageServiceImpTest {

    private ImageServiceImp imageServiceImp;
    private ModelMapper modelMapper;
    private PagedResourcesAssembler<Image> pagedResourcesAssembler;
    private Image firstImage;
    private Image secondImage;

    @Mock
    private ImageDao imageDao;

    @BeforeEach
    public void before() {
        modelMapper = new ModelMapper();
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        this.imageServiceImp = new ImageServiceImp(imageDao,modelMapper,pagedResourcesAssembler);
        List<Image> images = createImages();
        this.firstImage = images.get(0);
        this.secondImage = images.get(1);
    }

    static List<Image> createImages() {
        Image firstImage = Image.builder().id(UUID.randomUUID()).name("image").type("png").build();
        Image secondImage = Image.builder().id(UUID.randomUUID()).name("image").type("jpg").build();
        return List.of(firstImage,secondImage);
    }

    static boolean valid(Image image,ImageDto imageDto) {
        Assert.assertNotNull(imageDto);
        Assert.assertEquals(image.getId(),imageDto.getId());
        Assert.assertEquals(image.getName(),imageDto.getName());
        Assert.assertEquals(image.getType(),imageDto.getType());
        Assert.assertEquals(image.getOwner(),imageDto.getOwner());
        Assert.assertEquals(image.getImage(),imageDto.getImage());
        Assert.assertEquals(image.getCreatedDate(),imageDto.getCreatedDate());
        return true;
    }

    @Test
    void getImages() {
    }

    @Test
    void getImage() {
        given(this.imageDao.findById(this.firstImage.getId())).willReturn(Optional.of(this.firstImage));
        given(this.imageDao.findById(this.secondImage.getId())).willReturn(Optional.of(this.secondImage));
        ImageDto firstImage = this.imageServiceImp.getImage(this.firstImage.getId());
        ImageDto secondImage = this.imageServiceImp.getImage(this.secondImage.getId());
        Assert.assertTrue(valid(this.firstImage,firstImage));
        Assert.assertTrue(valid(this.secondImage,secondImage));
    }

    @Test
    void getImagesByName() {
    }

    @Test
    void getImagesByType() {
    }
}