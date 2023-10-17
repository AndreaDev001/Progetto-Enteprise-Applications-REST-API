package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateCategoryDto;
import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.services.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryDao categoryDao;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto getCategory(UUID categoryID) {
        Category category = this.categoryDao.findById(categoryID).orElseThrow();
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryDao.findAll();
        return categories.stream().map(category -> this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<String> getPrimaryCategories() {
        return this.categoryDao.getPrimaryCategories();
    }

    @Override
    public List<String> getCategoriesByPrimary(String primary) {
        return this.categoryDao.getCategoriesByPrimary(primary);
    }

    @Override
    public List<String> getCategoriesBySecondary(String primary,String secondary) {
        return this.categoryDao.getCategoriesBySecondary(primary,secondary);
    }

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setPrimaryCat(createCategoryDto.getPrimary());
        category.setSecondaryCat(createCategoryDto.getSecondary());
        category.setTertiaryCat(createCategoryDto.getTertiary());
        return this.modelMapper.map(this.categoryDao.save(category),CategoryDto.class);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryID) {
        this.categoryDao.findById(categoryID).orElseThrow();
        this.categoryDao.deleteById(categoryID);
    }
}
