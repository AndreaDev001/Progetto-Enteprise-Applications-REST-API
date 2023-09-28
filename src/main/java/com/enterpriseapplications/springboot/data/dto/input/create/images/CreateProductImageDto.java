package com.enterpriseapplications.springboot.data.dto.input.create.images;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductImageDto
{

    @NotNull
    @PositiveOrZero
    private Long productID;

    @NotNull
    @Size(min = 1,max = 10)
    private List<MultipartFile> files;
}
