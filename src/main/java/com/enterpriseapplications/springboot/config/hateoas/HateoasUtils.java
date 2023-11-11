package com.enterpriseapplications.springboot.config.hateoas;


import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;

public class HateoasUtils
{
    public static String convert(Object object) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();

        Field[] fields = object.getClass().getDeclaredFields();
        stringBuilder.append("?");
        for(int i = 0;i < fields.length;i++) {
            Field current = fields[i];
            current.setAccessible(true);
            String name = current.getName();
            String value = current.get(object).toString();
            stringBuilder.append(name);
            stringBuilder.append("=");
            stringBuilder.append(value);
            if(i != fields.length - 1)
                stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }
    public static <R> R convertToType(Object source, Class<R> resultClass, ModelMapper modelMapper) {
        return modelMapper.map(source, resultClass);
    }
}
