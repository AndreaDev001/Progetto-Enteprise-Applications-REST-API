package com.enterpriseapplications.springboot.config;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/documentation/**").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/orders/**").permitAll()
                        .requestMatchers("/reviews/**").permitAll()
                        .requestMatchers("/reports/**").permitAll()
                        .requestMatchers("/productReports/**").permitAll()
                        .requestMatchers("/messageReports/**").permitAll()
                        .requestMatchers("/messages/**").permitAll()
                        .requestMatchers("/bans/**").permitAll()
                        .requestMatchers("/offers/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers(("/users/**")).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
