package com.mladin.forum.security;

import com.mladin.forum.email.ForumEmailProvider;
import com.mladin.forum.security.oauth2.ForumOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.logging.Logger;

@EnableWebSecurity
@Configuration
public class ForumSecurity {
    @Autowired
    private ForumAuthProvider forumAuthProvider;

    @Autowired
    private ForumOAuth2Service forumOAuth2Service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }).sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.maximumSessions(1).sessionRegistry(sessionRegistry());
        }).authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers(
                    "/login", "/sign_in", "/sign_up", "/two_factor", "/oauth2/**", "/login/**", "/error",
                    "/css/fonts.css", "/css/sign_in.css", "/css/sign_up.css", "/css/error.css", "/css/form.css", "/css/two_factor.css", "/css/notification.css",
                    "/js/jquery.min.js", "/js/notification.js", "/js/goto.js",
                    "/fonts/**",
                    "/images/**").permitAll().anyRequest().authenticated();
        }).authenticationProvider(forumAuthProvider).formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer.loginPage("/sign_in").loginProcessingUrl("/login").failureUrl("/sign_in?error=true").defaultSuccessUrl("/");
        }).oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer.loginPage("/oauth2").failureUrl("/sign_in?error=true").userInfoEndpoint(userInfoEndpointConfig -> {
                userInfoEndpointConfig.userService(forumOAuth2Service);
            });
        }).logout(httpSecurityLogoutConfigurer -> {
            httpSecurityLogoutConfigurer.logoutUrl("/sign_out").logoutSuccessUrl("/sign_in").clearAuthentication(true).deleteCookies("JSESSIONID");
        });

        return http.build();
    }

    @Bean
    public SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ForumEmailProvider forumEmailProvider() {
        return new ForumEmailProvider();
    }
}
