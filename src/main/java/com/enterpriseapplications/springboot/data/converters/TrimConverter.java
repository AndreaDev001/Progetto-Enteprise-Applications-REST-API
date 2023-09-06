package com.enterpriseapplications.springboot.data.converters;

import jakarta.persistence.AttributeConverter;

public class TrimConverter implements AttributeConverter<String,String>
{
    @Override
    public String convertToDatabaseColumn(String s) {
        return s.trim();
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s.trim();
    }
}
