package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.config.hateoas.GenericModelAssembler;
import com.enterpriseapplications.springboot.data.dto.output.FollowDto;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.Follow;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;

public abstract class GenericServiceImp<T,U extends GenericOutput<?>>
{
    protected final ModelMapper modelMapper;
    protected final GenericModelAssembler<T,U> modelAssembler;
    protected final PagedResourcesAssembler<T> pagedResourcesAssembler;

    public GenericServiceImp(ModelMapper modelMapper,Class<T> source,Class<U> destination,PagedResourcesAssembler<T> pagedResourcesAssembler) {
        this.modelMapper = modelMapper;
        this.modelAssembler = new GenericModelAssembler<>(source,destination,modelMapper);
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }
}
