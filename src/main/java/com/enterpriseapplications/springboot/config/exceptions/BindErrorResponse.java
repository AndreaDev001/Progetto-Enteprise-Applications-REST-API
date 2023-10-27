package com.enterpriseapplications.springboot.config.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;
public class BindErrorResponse extends ErrorResponse
{
    @JsonProperty("fieldErrors")
    private final Map<String,String> errors;

    public BindErrorResponse(Date date,String uri,String code,String message,Map<String,String> errors) {
        super(date,uri,code,message);
        this.errors = errors;
    }
}
