package com.enterpriseapplications.springboot.services.implementations.images;

import com.enterpriseapplications.springboot.data.dao.images.ImageDao;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import com.enterpriseapplications.springboot.data.entities.images.Image;
import com.enterpriseapplications.springboot.services.GenericTestImp;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ImageServiceImpTest extends GenericTestImp<Image,ImageDto> {

    private ImageServiceImp imageServiceImp;
    @Mock
    private ImageDao imageDao;

    @Override
    protected void init() {
        super.init();
        this.imageServiceImp = new ImageServiceImp(imageDao,modelMapper,pagedResourcesAssembler);
        List<Image> images = createImages();
        this.firstElement = images.get(0);
        this.secondElement = images.get(1);
        this.elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
        this.defaultBefore();
    }

    static List<Image> createImages() {
        Image firstImage = Image.builder().id(UUID.randomUUID()).type(ImageType.IMAGE_PNG).build();
        Image secondImage = Image.builder().id(UUID.randomUUID()).type(ImageType.IMAGE_JPEG).build();
        return List.of(firstImage,secondImage);
    }

    static boolean baseValid(Image image,ImageDto imageDto) {
        Assert.assertNotNull(imageDto);
        Assert.assertEquals(image.getId(),imageDto.getId());
        Assert.assertEquals(image.getType(),imageDto.getType());
        Assert.assertEquals(image.getImage(),imageDto.getImage());
        Assert.assertEquals(image.getCreatedDate(),imageDto.getCreatedDate());
        return true;
    }

    @Override
    protected boolean valid(Image entity, ImageDto dto) {
        return baseValid(entity,dto);
    }

    @Test
    void getImages() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.imageDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ImageDto> images = this.imageServiceImp.getImages(pageRequest);
        Assert.assertTrue(compare(elements,images.getContent().stream().toList()));
        Assert.assertTrue(validPage(images,20,0,1,2));
    }

    @Test
    void getImage() {
        given(this.imageDao.findById(this.firstElement.getId())).willReturn(Optional.of(this.firstElement));
        given(this.imageDao.findById(this.secondElement.getId())).willReturn(Optional.of(this.secondElement));
        ImageDto firstImage = this.imageServiceImp.getImage(this.firstElement.getId());
        ImageDto secondImage = this.imageServiceImp.getImage(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstImage));
        Assert.assertTrue(valid(this.secondElement,secondImage));
    }
    @Test
    void getImagesByType() {
        given(this.imageDao.getImagesByType(ImageType.IMAGE_PNG)).willReturn(elements);
        List<ImageDto> images = this.imageServiceImp.getImagesByType(ImageType.IMAGE_PNG);
        Assert.assertTrue(compare(elements,images));
    }
}