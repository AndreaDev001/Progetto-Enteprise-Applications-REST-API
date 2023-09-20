package com.enterpriseapplications.springboot.data.dto.input.update;


import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto
{
    private String name;
    private String surname;
    private String description;
    private Gender gender;
}
