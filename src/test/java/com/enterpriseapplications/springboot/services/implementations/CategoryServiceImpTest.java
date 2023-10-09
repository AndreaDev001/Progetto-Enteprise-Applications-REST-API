package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest extends GenericTestImp<Category,CategoryDto>
{
    private CategoryServiceImp categoryServiceImp;

    @Mock
    public CategoryDao categoryDao;

    @Override
    protected void init() {
        super.init();
        categoryServiceImp = new CategoryServiceImp(categoryDao,modelMapper);
        firstElement = Category.builder().id(UUID.randomUUID()).primaryCat("Primary").secondaryCat("Secondary").tertiaryCat("Tertiary").build();
        secondElement = Category.builder().id(UUID.randomUUID()).primaryCat("Tertiary").secondaryCat("Secondary").tertiaryCat("Primary").build();
        elements = List.of(firstElement,secondElement);
    }


    @BeforeEach
    public void before() {
        init();
    }

    @Override
    public boolean valid(Category category, CategoryDto categoryDto) {
        Assert.assertNotNull(categoryDto);
        Assert.assertEquals(category.getId(),categoryDto.getId());
        Assert.assertEquals(category.getPrimaryCat(),categoryDto.getPrimary());
        Assert.assertEquals(category.getSecondaryCat(),categoryDto.getSecondary());
        Assert.assertEquals(category.getTertiaryCat(),categoryDto.getTertiary());
        Assert.assertEquals(category.getCreatedDate(),categoryDto.getCreatedDate());
        return true;
    }

    @Test
    void getCategory() {
    }

    @Test
    void getCategories() {
        given(this.categoryDao.findAll()).willReturn(elements);
        List<CategoryDto> categories = this.categoryServiceImp.getCategories();
        Assert.assertTrue(compare(elements,categories));
    }

    @Test
    void getPrimaryCategories() {

    }

    @Test
    void getCategoriesByPrimary() {
    }

    @Test
    void getCategoriesBySecondary() {
    }

    @Test
    void createCategory() {
    }
}