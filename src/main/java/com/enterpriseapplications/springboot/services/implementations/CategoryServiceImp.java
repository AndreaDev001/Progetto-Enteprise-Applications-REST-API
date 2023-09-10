package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.CategoryDao;
import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.data.entities.Category;
import com.enterpriseapplications.springboot.services.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<String> getCategories() {
        return this.categoryDao.getCategories();
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
    @Transactional
    public void deleteCategory(Long categoryID) {
        this.categoryDao.findById(categoryID).orElseThrow();
        this.categoryDao.deleteById(categoryID);
    }
}
