package com.enterpriseapplications.springboot.services;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class TestUtils
{

    @SneakyThrows
    public static <T> void generateValues(T entity)  {
        Class<?> value = entity.getClass();
        Field[] field = entity.getClass().getDeclaredFields();
        for (Field current : field) {
            current.setAccessible(true);
            if (current.getType().isAssignableFrom(Set.class)) {
                Set<?> set = new HashSet<>();
                current.set(entity, set);
            }
        }
    }
}
