package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping("{categoryID}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryID") UUID categoryID) {
        return ResponseEntity.ok(this.categoryService.getCategory(categoryID));
    }

    @GetMapping("/primaries")
    public ResponseEntity<List<String>> getPrimaryCategories() {
        return ResponseEntity.ok(this.categoryService.getPrimaryCategories());
    }

    @GetMapping("/secondaries/{primary}")
    public ResponseEntity<List<String>> getCategoriesByPrimary(@PathVariable("primary") String primary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesByPrimary(primary));
    }

    @GetMapping("/tertiaries/{primary}/{secondary}")
    public ResponseEntity<List<String>> getCategoriesBySecondary(@PathVariable("primary") String primary,@PathVariable("secondary") String secondary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesBySecondary(primary,secondary));
    }

    @DeleteMapping("{categoryID}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryID") UUID categoryID) {
        this.categoryService.deleteCategory(categoryID);
        return ResponseEntity.noContent().build();
    }
}
