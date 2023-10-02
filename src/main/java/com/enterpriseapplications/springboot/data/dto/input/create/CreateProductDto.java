package com.enterpriseapplications.springboot.data.dto.input.create;


import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto
{
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal minPrice;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    private ProductCondition condition;

    @NotNull
    private ProductVisibility visibility;

    @NotNull
    @NotBlank
    private String primaryCat;

    @NotNull
    @NotBlank
    private String secondaryCat;

    @NotNull
    @NotBlank
    private String tertiaryCat;
}
