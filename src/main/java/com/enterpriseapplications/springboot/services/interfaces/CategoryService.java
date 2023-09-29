package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService
{
    List<CategoryDto> getCategories();
    List<String> getPrimaryCategories();
    List<String> getCategoriesByPrimary(String primary);
    List<String> getCategoriesBySecondary(String primary,String secondary);
    void deleteCategory(UUID categoryID);
}
