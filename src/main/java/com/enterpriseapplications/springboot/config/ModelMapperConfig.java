package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.dto.output.UserRef;
import com.enterpriseapplications.springboot.data.entities.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class ModelMapperConfig
{

    public Converter<User,UserRef> userRefConverter = new AbstractConverter<User, UserRef>() {
        @Override
        protected UserRef convert(User user) {
            UserRef userRef = new UserRef();
            userRef.setId(user.getId());
            userRef.setUsername(user.getUsername());
            userRef.addLinks();
            return userRef;
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.addConverter(userRefConverter);
        return modelMapper;
    }
}
