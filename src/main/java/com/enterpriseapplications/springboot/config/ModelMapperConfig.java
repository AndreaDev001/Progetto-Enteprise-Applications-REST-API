package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.refs.*;
import com.enterpriseapplications.springboot.data.entities.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
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
    public Converter<Address, AddressRef> addressRefConverter = new AbstractConverter<Address, AddressRef>() {
        @Override
        protected AddressRef convert(Address address) {
            return new AddressRef(address);
        }
    };
    public Converter<PaymentMethod,PaymentMethodRef> paymentMethodRefConverter = new AbstractConverter<PaymentMethod, PaymentMethodRef>() {
        @Override
        protected PaymentMethodRef convert(PaymentMethod paymentMethod) {
            return new PaymentMethodRef(paymentMethod);
        }
    };
    public Converter<Conversation,ConversationRef> conversationRefConverter = new AbstractConverter<Conversation,ConversationRef>() {
        @Override
        protected ConversationRef convert(Conversation conversation) {
            return new ConversationRef(conversation);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.addConverter(userRefConverter);
        modelMapper.addConverter(productRefConverter);
        modelMapper.addConverter(addressRefConverter);
        modelMapper.addConverter(paymentMethodRefConverter);
        modelMapper.addConverter(conversationRefConverter);
        return modelMapper;
    }
}
