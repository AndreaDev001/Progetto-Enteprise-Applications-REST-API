package com.enterpriseapplications.springboot.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info =
@Info(title = "Documentazione Progetto Enterprise Applications",description = "API REST Enterprise Applications",version = "1.00"))
@SecurityScheme(name = "Authorization",type = SecuritySchemeType.HTTP,in = SecuritySchemeIn.HEADER,scheme = "bearer",bearerFormat = "JWT")
public class OpenApiConfig
{

}
