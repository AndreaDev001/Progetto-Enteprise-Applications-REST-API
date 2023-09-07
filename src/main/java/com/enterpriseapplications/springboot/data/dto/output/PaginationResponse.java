package com.enterpriseapplications.springboot.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {

    private List<T> results;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
