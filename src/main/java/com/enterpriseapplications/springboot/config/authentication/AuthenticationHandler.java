package com.enterpriseapplications.springboot.config.authentication;


import com.enterpriseapplications.springboot.config.CacheConfig;
import com.enterpriseapplications.springboot.data.dao.BanDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.images.UserImageDao;
import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.Gender;
import com.enterpriseapplications.springboot.data.entities.enums.ImageType;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import com.enterpriseapplications.springboot.data.entities.images.UserImage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler
{
    private final UserDao userDao;
    private final UserImageDao userImageDao;
    private final BanDao banDao;


    @Transactional
    public void handleSuccess(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userID = UUID.fromString((String)jwtAuthenticationToken.getTokenAttributes().get("sub"));
        Optional<User> userOptional = this.userDao.findById(userID);
        if(userOptional.isEmpty()) {
            String username = (String)jwtAuthenticationToken.getTokenAttributes().get("username");
            String email = (String)jwtAuthenticationToken.getTokenAttributes().get("email");
            String name = (String)jwtAuthenticationToken.getTokenAttributes().get("name");
            String surname = (String)jwtAuthenticationToken.getTokenAttributes().get("surname");
            User requiredUser = User.builder().id(userID).username(username).email(email).name(name).surname(surname).gender(Gender.NOT_SPECIFIED).visibility(UserVisibility.PUBLIC).build();
            this.userDao.save(requiredUser);
        }
    }

    public void handleFailure(Authentication authentication) {

    }


    @Cacheable(value = CacheConfig.CACHE_BANNED_USERS,key = "#jwtAuthenticationToken.name")
    public boolean isBanned(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userID = UUID.fromString((String)jwtAuthenticationToken.getTokenAttributes().get("sub"));
        Optional<User> userOptional = this.userDao.findById(userID);
        if(userOptional.isPresent()) {
            Optional<Ban> banOptional = this.banDao.findBan(userID);
            return banOptional.isPresent();
        }
        return false;
    }
}
