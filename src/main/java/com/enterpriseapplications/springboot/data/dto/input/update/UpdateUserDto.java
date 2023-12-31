package com.enterpriseapplications.springboot.data.dto.input.update;


import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto
{
    private String name;
    private String surname;
    private String description;
    private Gender gender;
    private UserVisibility visibility;
}
