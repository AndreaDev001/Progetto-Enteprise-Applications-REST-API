package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dto.output.ConversationDto;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.dto.output.ProductDto;
import com.enterpriseapplications.springboot.data.dto.output.UserDetailsDto;
import com.enterpriseapplications.springboot.data.dto.output.refs.*;
import com.enterpriseapplications.springboot.data.entities.*;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

import java.util.UUID;

@Configuration
public class ModelMapperConfig
{

    public static Converter<Product,ProductDto> productConverter = new Converter<Product, ProductDto>() {
        @Override
        public ProductDto convert(MappingContext<Product, ProductDto> mappingContext) {
            mappingContext.getDestination().setAmountOfLikes(mappingContext.getSource().getReceivedLikes().size());
            return mappingContext.getDestination();
        }
    };
    public static Converter<User,UserDetailsDto> userConverter = new Converter<User, UserDetailsDto>() {
        @Override
        public UserDetailsDto convert(MappingContext<User, UserDetailsDto> mappingContext) {
            User source = mappingContext.getSource();
            UserDetailsDto destination = mappingContext.getDestination();
            destination.setAmountOfFollowers(source.getFollowers().size());
            destination.setAmountOfFollowed(source.getFollows().size());
            destination.setAmountOfProducts(source.getProducts().size());
            destination.setAmountOfLikes(source.getCreatedLikes().size());
            destination.setAmountOfWrittenReviews(source.getWrittenReviews().size());
            destination.setAmountOfReceivedReviews(source.getReceivedReviews().size());
            destination.setAmountOfReplies(source.getWrittenReplies().size());
            destination.setAmountOfReceivedBans(source.getCreatedBans().size());
            return destination;
        }
    };

    public static Converter<User,UserRef> userRefConverter = new AbstractConverter<User, UserRef>() {
        @Override
        protected UserRef convert(User user) {
            return new UserRef(user);
        }
    };
    public static Converter<Product, ProductRef> productRefConverter = new AbstractConverter<Product, ProductRef>() {
        @Override
        protected ProductRef convert(Product product) {
            return new ProductRef(product);
        }
    };
    public static Converter<Address, AddressRef> addressRefConverter = new AbstractConverter<Address, AddressRef>() {
        @Override
        protected AddressRef convert(Address address) {
            return new AddressRef(address);
        }
    };
    public static Converter<PaymentMethod,PaymentMethodRef> paymentMethodRefConverter = new AbstractConverter<PaymentMethod, PaymentMethodRef>() {
        @Override
        protected PaymentMethodRef convert(PaymentMethod paymentMethod) {
            return new PaymentMethodRef(paymentMethod);
        }
    };
    public static Converter<Conversation,ConversationRef> conversationRefConverter = new AbstractConverter<Conversation,ConversationRef>() {
        @Override
        protected ConversationRef convert(Conversation conversation) {
            return new ConversationRef(conversation);
        }
    };

    public static ModelMapper generateModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.addConverter(userRefConverter);
        modelMapper.addConverter(productRefConverter);
        modelMapper.addConverter(addressRefConverter);
        modelMapper.addConverter(paymentMethodRefConverter);
        modelMapper.addConverter(conversationRefConverter);
        modelMapper.createTypeMap(Product.class,ProductDto.class).setPostConverter(productConverter);
        modelMapper.createTypeMap(User.class,UserDetailsDto.class).setPostConverter(userConverter);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return generateModelMapper();
    }
}
