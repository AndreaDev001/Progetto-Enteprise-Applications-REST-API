package com.enterpriseapplications.springboot.data.dto.input.images;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateImageDto
{
    @NotNull
    @PositiveOrZero
    protected Long ownerID;
}
