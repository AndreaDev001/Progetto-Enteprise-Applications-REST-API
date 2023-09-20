package com.enterpriseapplications.springboot.config.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String remoteIPAddress = request.getRemoteAddr();
        String userID = "None";
        String username = "None";
        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken authenticationToken) {
            userID = String.valueOf(authenticationToken.getName());
            username = String.valueOf(authenticationToken.getTokenAttributes().get("username"));
        }
        log.info("Response to User {},username {}, ip address {} to request {} with response code {}",userID,username,remoteIPAddress,request.getRequestURI(),response.getStatus());
    }
}
