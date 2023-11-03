package com.enterpriseapplications.springboot.config.util;

import com.enterpriseapplications.springboot.config.exceptions.InvalidMediaType;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils
{
    public static ImageType getImageType(String type) {
        for(ImageType current : ImageType.values()) {
            if(current.getName().equals(type))
                return current;
        }
        throw new InvalidMediaType();
    }
}
