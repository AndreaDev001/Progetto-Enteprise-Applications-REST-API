package com.enterpriseapplications.springboot.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AccessManager
{
    public boolean hasRole(Authentication authentication,String role) {
        if(authentication instanceof JwtAuthenticationToken authenticationToken) {
            String[] values = (String[])authenticationToken.getTokenAttributes().get("authorities");
            for(String current : values)
                if(current.equals(role))
                    return true;
        }
        return false;
    }
}
