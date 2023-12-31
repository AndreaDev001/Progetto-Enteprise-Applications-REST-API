package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateCategoryDto;
import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService
{
    CategoryDto getCategory(UUID categoryID);
    List<CategoryDto> getCategories();
    List<String> getPrimaryCategories();
    List<String> getCategoriesByPrimary(String primary);
    List<String> getCategoriesBySecondary(String primary,String secondary);
    CategoryDto createCategory(CreateCategoryDto createCategoryDto);
    void deleteCategory(UUID categoryID);
}
