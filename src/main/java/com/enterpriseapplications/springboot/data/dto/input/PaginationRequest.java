package com.enterpriseapplications.springboot.data.dto.input;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    @NotNull
    @PositiveOrZero
    private Integer page;
    @NotNull
    @PositiveOrZero
    @Max(20)
    private Integer pageSize;
}
