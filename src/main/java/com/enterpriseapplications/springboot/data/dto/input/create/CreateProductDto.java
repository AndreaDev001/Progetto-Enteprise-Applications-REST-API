package com.enterpriseapplications.springboot.data.dto.input.create;


import com.enterpriseapplications.springboot.data.entities.enums.ProductCondition;
import com.enterpriseapplications.springboot.data.entities.enums.ProductVisibility;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductDto
{
    @NotNull
    @NotBlank
    @Length(min = 3,max = 20)
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 10,max = 200)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal minPrice;

    @NotNull
    @NotBlank
    @Length(min = 5,max = 20)
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
