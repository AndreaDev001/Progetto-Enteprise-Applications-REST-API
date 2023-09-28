package com.enterpriseapplications.springboot;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


public class GenericModelAssembler<T,U extends RepresentationModel<?>> extends RepresentationModelAssemblerSupport<T,U> {

    private final ModelMapper modelMapper;

    public GenericModelAssembler(Class<?> controllerClass, Class<U> resourceType,ModelMapper modelMapper) {
        super(controllerClass, resourceType);
        this.modelMapper = modelMapper;
    }

    @Override
    public U toModel(T entity) {
        return HateoasUtils.convertToType(entity,this.getResourceType(),modelMapper);
    }
}
