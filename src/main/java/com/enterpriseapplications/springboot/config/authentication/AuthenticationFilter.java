package com.enterpriseapplications.springboot.config.authentication;

import com.enterpriseapplications.springboot.config.exceptions.BannedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter
{
    private final AuthenticationHandler authenticationHandler;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken && authenticationHandler.isBanned(jwtAuthenticationToken))
        {
            String userID = jwtAuthenticationToken.getTokenAttributes().get("sub").toString();
            String username = jwtAuthenticationToken.getTokenAttributes().get("username").toString();
            String remoteIPAddress = request.getRemoteAddr();
            log.info("Response to User {},username {}, ip address {} to request {} with response code {}",userID,username,remoteIPAddress,request.getRequestURI(), HttpStatusCode.valueOf(401));
            handlerExceptionResolver.resolveException(request,response,null,new BannedException());
        }
        else
            filterChain.doFilter(request,response);
    }
}
