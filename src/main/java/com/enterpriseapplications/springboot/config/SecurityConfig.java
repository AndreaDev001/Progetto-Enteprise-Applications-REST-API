package com.enterpriseapplications.springboot.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint));
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/documentation/**").permitAll()
                        .requestMatchers("/products/public/**").authenticated()
                        .requestMatchers("/orders/public/**").permitAll()
                        .requestMatchers("/reviews/public/**").permitAll()
                        .requestMatchers("/reports/public/**").permitAll()
                        .requestMatchers("/productReports/public/**").permitAll()
                        .requestMatchers("/messageReports/public/**").permitAll()
                        .requestMatchers("/paymentMethods/public/**").permitAll()
                        .requestMatchers("/replies/public/**").permitAll()
                        .requestMatchers("/follows/public/**").permitAll()
                        .requestMatchers("/categories/public/**").permitAll()
                        .requestMatchers("/messages/public/**").permitAll()
                        .requestMatchers("/bans/public/**").permitAll()
                        .requestMatchers("/offers/public/**").permitAll()
                        .requestMatchers("/images/public/**").permitAll()
                        .requestMatchers("/userImages/public/**").permitAll()
                        .requestMatchers("/productImages/public/**").permitAll()
                        .requestMatchers(("/users/public/**")).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
