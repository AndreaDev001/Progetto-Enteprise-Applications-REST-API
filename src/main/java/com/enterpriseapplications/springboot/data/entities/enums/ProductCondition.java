package com.enterpriseapplications.springboot.data.entities.enums;


import lombok.Getter;

@Getter
public enum ProductCondition
{
    NEW("New"),
    ALMOST_NEW("Barely used"),
    USED("Used");

    private final String name;

    ProductCondition(String name) {
        this.name = name;
    }
}
