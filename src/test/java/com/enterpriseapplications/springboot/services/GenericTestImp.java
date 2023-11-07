package com.enterpriseapplications.springboot.services;

import com.enterpriseapplications.springboot.config.ModelMapperConfig;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import com.enterpriseapplications.springboot.data.entities.User;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.util.*;

import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class GenericTestImp<T,U extends GenericOutput<?>>
{
    protected MockHttpServletRequest httpServletRequest;
    protected PagedResourcesAssembler<T> pagedResourcesAssembler;
    protected ModelMapper modelMapper;
    protected T firstElement;
    protected T secondElement;
    protected List<T> elements;
    protected User authenticatedUser;

    protected void init() {
        authenticatedUser = User.builder().id(UUID.randomUUID()).build();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(authenticatedUser.getId().toString());
        SecurityContextHolder.setContext(securityContext);
        httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setContextPath("/api/v1");
        pagedResourcesAssembler = new PagedResourcesAssembler<>(null,null);
        modelMapper = ModelMapperConfig.generateModelMapper();
        ServletRequestAttributes attributes = new ServletRequestAttributes(httpServletRequest);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @SneakyThrows
    protected void defaultBefore() {
        this.generateValues(firstElement);
        this.generateValues(secondElement);
    }


    @SneakyThrows
    protected void generateValues(T entity) throws IllegalAccessException {
        TestUtils.generateValues(entity);
    }

    protected abstract boolean valid(T entity, U dto);

    @SneakyThrows
    protected boolean validPage(PagedModel<U> pagedModel,long size,long page,long totalPages,long totalElements) {
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
