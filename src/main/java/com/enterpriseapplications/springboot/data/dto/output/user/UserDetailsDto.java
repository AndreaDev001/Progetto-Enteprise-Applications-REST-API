package com.enterpriseapplications.springboot.data.dto.output.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto extends RepresentationModel<UserDetailsDto> {
    private String username;
    private String name;
    private String surname;
    private String description;
}
