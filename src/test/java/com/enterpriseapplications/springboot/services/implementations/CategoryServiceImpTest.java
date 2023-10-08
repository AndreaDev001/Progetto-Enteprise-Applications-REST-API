package com.enterpriseapplications.springboot.services.implementations;

import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {

    boolean valid(Category category, CategoryDto categoryDto) {
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