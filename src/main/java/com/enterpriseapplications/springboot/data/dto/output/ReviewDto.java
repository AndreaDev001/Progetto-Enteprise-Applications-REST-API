package com.enterpriseapplications.springboot.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto
{
    private UserRef writer;
    private UserRef receiver;
    private Integer rating;
    private String text;
}
