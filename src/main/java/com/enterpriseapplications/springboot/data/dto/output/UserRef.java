package com.enterpriseapplications.springboot.data.dto.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRef
{
    private Long id;
    private String username;
}
