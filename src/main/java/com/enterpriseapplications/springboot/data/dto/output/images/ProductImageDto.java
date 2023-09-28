package com.enterpriseapplications.springboot.data.dto.output.images;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductImageDto extends ImageDto
{
    private ProductRef product;
}
