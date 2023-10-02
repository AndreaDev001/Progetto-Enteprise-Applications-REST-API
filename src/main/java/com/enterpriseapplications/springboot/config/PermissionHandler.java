package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.entities.OwnableEntity;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionHandler
{
    public boolean hasRole(String requiredRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
                List<String> roles = (List<String>)jwtAuthenticationToken.getTokenAttributes().get("roles");
                for(String role : roles)
                    if(role.equals(requiredRole))
                        return true;
            }
        }
        return false;
    }
    public boolean isProvider(String requiredProvider) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
                String provider = (String)jwtAuthenticationToken.getTokenAttributes().get("provider");
                return provider.equals(requiredProvider);
            }
        }
        return false;
    }

    public boolean hasAccess(JpaRepository<OwnableEntity,UUID> repository, UUID resourceID) {
        if(hasRole("ROLE_ADMIN"))
            return true;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
                String sub = (String)jwtAuthenticationToken.getTokenAttributes().get("sub");
                UUID userID = UUID.fromString(sub);
                OwnableEntity ownableEntity = repository.findById(resourceID).orElseThrow();
                return ownableEntity.getOwnerID().equals(userID);
            }
        }
        return false;
    }
    public boolean hasAccess(UUID userID) {
        if(hasRole("ROLE_ADMIN"))
            return true;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
                String sub = (String)jwtAuthenticationToken.getTokenAttributes().get("sub");
                UUID id = UUID.fromString(sub);
                return userID.equals(id);
            }
        }
        return false;
    }
}