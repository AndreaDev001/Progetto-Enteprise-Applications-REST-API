package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.entities.interfaces.MultiOwnableEntity;
import com.enterpriseapplications.springboot.data.entities.interfaces.OwnableEntity;
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
        UUID userID = this.readUserID();
        OwnableEntity ownableEntity = repository.findById(resourceID).orElseThrow();
        return ownableEntity.getOwnerID().equals(userID);
    }

    public boolean hasAccessMulti(JpaRepository<MultiOwnableEntity,UUID> repository,UUID resourceID) {
        if(hasRole("ROLE_ADMIN"))
            return true;
        UUID userID = this.readUserID();
        MultiOwnableEntity multiOwnableEntity = repository.findById(resourceID).orElseThrow();
        return multiOwnableEntity.getOwners().contains(userID);
    }

    private UUID readUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            String sub  = (String)jwtAuthenticationToken.getTokenAttributes().get("sub");
            UUID userID = UUID.fromString(sub);
            return userID;
        }
        return null;
    }
    public boolean hasAccess(UUID userID) {
        if(hasRole("ROLE_ADMIN"))
            return true;
        UUID requiredID = this.readUserID();
        return requiredID.equals(userID);
    }
}
