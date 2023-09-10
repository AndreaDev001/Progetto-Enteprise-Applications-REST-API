package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping("{primary}")
    public ResponseEntity<List<String>> getCategoriesByPrimary(@PathVariable("primary") String primary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesByPrimary(primary));
    }

    @GetMapping("{primary}/{secondary}")
    public ResponseEntity<List<String>> getCategoriesBySecondary(@PathVariable("primary") String primary,@PathVariable("secondary") String secondary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesBySecondary(primary,secondary));
    }
}
