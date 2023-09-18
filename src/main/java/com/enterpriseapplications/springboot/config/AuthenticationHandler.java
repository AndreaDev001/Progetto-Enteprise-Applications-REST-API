package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler
{
    private final UserDao userDao;


    @Transactional
    public void handleSuccess(JwtAuthenticationToken jwtAuthenticationToken) {
        Long userID = Long.valueOf((String)jwtAuthenticationToken.getTokenAttributes().get("sub"));
        Optional<User> userOptional = this.userDao.findById(userID);
        if(userOptional.isEmpty()) {
            String username = (String)jwtAuthenticationToken.getTokenAttributes().get("username");
            String email = (String)jwtAuthenticationToken.getTokenAttributes().get("email");
            User requiredUser = new User();
            requiredUser.setId(userID);
            requiredUser.setUsername(username);
            requiredUser.setEmail(email);
            requiredUser.setGender(Gender.NOT_SPECIFIED);
            this.userDao.save(requiredUser);
        }
    }
    public void handleFailure(Authentication authentication) {

    }
}
