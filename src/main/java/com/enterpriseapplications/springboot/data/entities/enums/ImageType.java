package com.enterpriseapplications.springboot.data.entities.enums;

import lombok.Getter;

@Getter
public enum ImageType {

    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private String name;
    ImageType(String name) {
        this.name = name;
    }
}
