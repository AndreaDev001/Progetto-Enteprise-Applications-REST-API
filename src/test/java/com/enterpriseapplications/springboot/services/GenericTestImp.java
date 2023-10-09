package com.enterpriseapplications.springboot.services;

import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;

public abstract class GenericTestImp<T,U extends GenericOutput<?>>
{
    protected MockHttpServletRequest httpServletRequest;
    protected PagedResourcesAssembler<T> pagedResourcesAssembler;
    protected ModelMapper modelMapper;
    protected T firstElement;
    protected T secondElement;
    protected List<T> elements;

    protected void init() {
        httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setContextPath("/api/v1");
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        modelMapper = new ModelMapper();
        ServletRequestAttributes attributes = new ServletRequestAttributes(httpServletRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    protected abstract boolean valid(T entity, U dto);

    @SneakyThrows
    boolean validPage(PagedModel<U> pagedModel,long size,long page,long totalPages,long totalElements) {
        Assert.assertNotNull(pagedModel);;
        Assert.assertEquals(pagedModel.getMetadata().getSize(),size);
        Assert.assertEquals(pagedModel.getMetadata().getNumber(),page);
        Assert.assertEquals(pagedModel.getMetadata().getTotalPages(),totalPages);
        Assert.assertEquals(pagedModel.getMetadata().getTotalElements(),totalElements);
        return true;
    }
    protected boolean compare(List<T> entities,List<U> content) {
        Assert.assertNotNull(entities);
        Assert.assertNotNull(content);
        Assert.assertEquals(entities.size(),content.size());
        for(int i = 0;i < entities.size();i++) {
            T entity = entities.get(i);
            U dto = content.get(i);
            Assert.assertTrue(valid(entity,dto));
        }
        return true;
    }
}
