package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto
{
    private UserRef buyer;
    private UserRef seller;
    private ProductRef product;
    private LocalDate createdDate;
}
