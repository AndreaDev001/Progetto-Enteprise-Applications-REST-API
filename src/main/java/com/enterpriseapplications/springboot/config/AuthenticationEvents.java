package com.enterpriseapplications.springboot.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents
{
    private final AuthenticationHandler authenticationHandler;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent authenticationSuccessEvent) {
        if(authenticationSuccessEvent.getAuthentication() instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            this.authenticationHandler.handleSuccess(jwtAuthenticationToken);
        }
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent authenticationFailureEvent) {
        this.authenticationHandler.handleFailure(authenticationFailureEvent.getAuthentication());
    }
}
