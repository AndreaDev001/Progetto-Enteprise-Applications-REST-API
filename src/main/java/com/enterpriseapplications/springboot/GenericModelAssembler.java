package com.enterpriseapplications.springboot;

import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


public class GenericModelAssembler<T,U extends GenericOutput<?>> extends RepresentationModelAssemblerSupport<T,U> {

    private final ModelMapper modelMapper;

    public GenericModelAssembler(Class<?> controllerClass, Class<U> resourceType,ModelMapper modelMapper) {
        super(controllerClass, resourceType);
        this.modelMapper = modelMapper;
    }

    @Override
    public U toModel(T entity) {
        U result = HateoasUtils.convertToType(entity,this.getResourceType(),modelMapper);
        result.addLinks();
        return result;
    }
}
