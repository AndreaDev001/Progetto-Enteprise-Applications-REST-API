package com.enterpriseapplications.springboot.data.converters;

import jakarta.persistence.AttributeConverter;

public class TrimConverter implements AttributeConverter<String,String>
{
    @Override
    public String convertToDatabaseColumn(String s) {
        if(s != null)
            return s.trim();
        return null;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if(s != null)
            return s.trim();
        return null;
    }
}
