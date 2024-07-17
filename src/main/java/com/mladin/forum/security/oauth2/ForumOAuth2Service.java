package com.mladin.forum.security.oauth2;

import com.mladin.forum.security.ForumUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ForumOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Autowired
    private ForumUserManager forumUserManager;

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return this.defaultOAuth2UserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        boolean email_verified = oAuth2User.getAttribute("email_verified");

        if(email_verified) {
            return forumUserManager.authenticateOAuth2Token(email);
        }

        throw new OAuth2AuthenticationException("Failed to authenticate.");
    }
}
