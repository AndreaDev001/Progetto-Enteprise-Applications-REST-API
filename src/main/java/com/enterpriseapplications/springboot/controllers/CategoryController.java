package com.enterpriseapplications.springboot.controllers;


import com.enterpriseapplications.springboot.data.dto.input.create.CreateCategoryDto;
import com.enterpriseapplications.springboot.data.dto.output.CategoryDto;
import com.enterpriseapplications.springboot.services.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class CategoryController
{
    private final CategoryService categoryService;


    @GetMapping("/public")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping("/public/{categoryID}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryID") UUID categoryID) {
        return ResponseEntity.ok(this.categoryService.getCategory(categoryID));
    }

    @GetMapping("/public/primaries")
    public ResponseEntity<List<String>> getPrimaryCategories() {
        return ResponseEntity.ok(this.categoryService.getPrimaryCategories());
    }

    @PostMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryDto createCategoryDto) {
        return ResponseEntity.ok(this.categoryService.createCategory(createCategoryDto));
    }

    @GetMapping("/public/secondaries/{primary}")
    public ResponseEntity<List<String>> getCategoriesByPrimary(@PathVariable("primary") String primary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesByPrimary(primary));
    }

    @GetMapping("/public/tertiaries/{primary}/{secondary}")
    public ResponseEntity<List<String>> getCategoriesBySecondary(@PathVariable("primary") String primary,@PathVariable("secondary") String secondary) {
        return ResponseEntity.ok(this.categoryService.getCategoriesBySecondary(primary,secondary));
    }

    @DeleteMapping("private/{categoryID}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryID") UUID categoryID) {
        this.categoryService.deleteCategory(categoryID);
        return ResponseEntity.noContent().build();
    }
}
