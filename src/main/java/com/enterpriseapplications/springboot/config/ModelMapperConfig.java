package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dto.output.refs.ProductRef;
import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{

    public Converter<User,UserRef> userRefConverter = new AbstractConverter<User, UserRef>() {
        @Override
        protected UserRef convert(User user) {
            return new UserRef(user);
        }
    };

    public Converter<Product, ProductRef> productRefConverter = new AbstractConverter<Product, ProductRef>() {
        @Override
        protected ProductRef convert(Product product) {
            return new ProductRef(product);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.addConverter(userRefConverter);
        modelMapper.addConverter(productRefConverter);
        return modelMapper;
    }
}
